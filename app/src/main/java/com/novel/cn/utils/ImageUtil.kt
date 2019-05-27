package com.novel.cn.utils

import android.content.Context
import android.graphics.*
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import io.reactivex.Single
import timber.log.Timber
import java.io.*


/**
 * 图片工具类、包含压缩图片获取图片路径等功能
 * Created by hy on 2018/8/4
 */
object ImageUtil {

    /**
     * 等比缩放图片到指定宽度
     */
    fun zoomBitmap(source: Bitmap, destWidth: Int): Bitmap {
        val result: Bitmap
        val destHeight = (source.height * ((destWidth + 0f) / source.width)).toInt()
        result = Bitmap.createBitmap(destWidth, destHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(result)
        val rect = Rect(0, 0, destWidth, destHeight)
        canvas.drawFilter = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        canvas.drawBitmap(source, null, rect, null)
        return result
    }

    /**
     * 压缩图片
     */
    fun compress(context: Context, source: File, destFile: File, options: CompressOptions = CompressOptions()) : Single<File> {
        return compressImpl(context, srcUri = Uri.fromFile(source), destFile = destFile, options = options)
    }

    /**
     * 压缩图片
     */
    fun compress(context: Context, source: Uri, destFile: File, options: CompressOptions = CompressOptions()) : Single<File> {
        return compressImpl(context, srcUri = source, destFile = destFile, options = options)
    }

    /**
     * 压缩图片
     */
    fun compress(context: Context, source: Bitmap, destFile: File, options: CompressOptions = CompressOptions()) : Single<File> {
        return compressImpl(context, srcBitmap = source, destFile = destFile, options = options)
    }

    /**
     * 压缩图片
     *
     * @param context
     * @param srcUri
     * @param srcBitmap
     * @param destFile
     * @param options
     * @return
     */
    private fun compressImpl(context: Context, srcUri: Uri? = null, srcBitmap: Bitmap? = null, destFile: File, options: CompressOptions = CompressOptions()): Single<File> {
        return Single.create { emitter ->
            var result: Bitmap

            /*
             * 尺寸压缩处理
             */
            if (srcUri != null) {
                // uri指向的文件路径
                val filePath = getFilePath(context, srcUri)
                if (filePath == null) {
                    emitter.onError(FileNotFoundException("找不到文件路径：$filePath"))
                    return@create
                }
                val bfo = BitmapFactory.Options()
                bfo.inJustDecodeBounds = true // 避免直接加载图片过大导致OOM
                BitmapFactory.decodeFile(filePath, bfo)
                bfo.inJustDecodeBounds = false
                bfo.inSampleSize = getRatioSize(bfo.outWidth, bfo.outHeight, options.maxWidth, options.maxHeight)
                result = BitmapFactory.decodeFile(filePath, bfo)
                result = fixCameraPhotoOrientation(context, result, srcUri)
            } else {
                if (srcBitmap == null) {
                    emitter.onError(IllegalArgumentException("源文件不能为空"))
                    return@create
                }
                val ratio = getRatioSize(srcBitmap.width, srcBitmap.height, options.maxWidth, options.maxHeight)
                val destWidth = srcBitmap.width / ratio
                val destHeight = srcBitmap.height / ratio
                result = Bitmap.createBitmap(destWidth, destHeight, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(result)
                val rect = Rect(0, 0, destWidth, destHeight)
                canvas.drawBitmap(srcBitmap, null, rect, null)
            }

            /*
             * 质量压缩处理
             */
            val baos = qualityCompress(options, result)

            /*
             * 保存图片文件
             */
            try {
                saveFile(baos, destFile)
            } catch (e: Exception) {
                emitter.onError(e)
                Timber.e(e)
                return@create
            }

            emitter.onSuccess(destFile)
        }
    }

    @Throws(IOException::class)
    private fun saveFile(baos: ByteArrayOutputStream, destFile: File) {
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(destFile)
            fos.write(baos.toByteArray())
            fos.flush()
        } finally {
            fos?.close()
        }
    }

    /**
     * 质量压缩处理
     */
    private fun qualityCompress(options: CompressOptions, result: Bitmap): ByteArrayOutputStream {
        val baos = ByteArrayOutputStream()
        // 100表示不压缩，把压缩后的数据存放到baos中
        var quality = options.quality
        result.compress(options.format, 100, baos)
        // 循环判断如果压缩后图片是否大于限制大小,大于继续压缩
        while (baos.toByteArray().size / 1024 > options.maxSize) {
            // 每次都减少5
            quality -= 5
            // 重置baos即清空baos
            baos.reset()
            // 这里压缩options%，把压缩后的数据存放到baos中
            result.compress(options.format, quality, baos)
        }
        if (!result.isRecycled) {
            result.recycle()
        }
        return baos
    }

    /**
     * 计算缩放比
     *
     * @param width  当前图片宽度
     * @param height 当前图片高度
     * @return int 缩放比
     */
    fun getRatioSize(width: Int, height: Int, maxWidth: Int, maxHeight: Int): Int {
        // 缩放比
        var ratio = 1
        // 缩放比,由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        if (width > height && width > maxWidth) {
            // 如果图片宽度比高度大,以宽度为基准
            ratio = width / maxWidth
            if (ratio == 1)
                ratio = 2
        } else if (width < height && height > maxHeight) {
            // 如果图片高度比宽度大，以高度为基准
            ratio = height / maxHeight
            if (ratio == 1)
                ratio = 2
        } else if (width == height && width + height > maxWidth + maxHeight) {
            // 如果图片宽高一致，同时宽高大于限制
            ratio = width / maxWidth
            if (maxWidth > maxHeight) {
                ratio = height / maxHeight
            }
            if (ratio == 1)
                ratio = 2
        }
        // 最小比率为1
        if (ratio <= 0)
            ratio = 1
        return ratio
    }

    /**
     * 获取文件的路径
     *
     * @param context
     * @param uri
     * @return
     */
    fun getFilePath(context: Context, uri: Uri): String? {
        var filePath: String? = null
        if ("content".equals(uri.scheme, ignoreCase = true)) {
            val cursor = context.contentResolver.query(uri, arrayOf(MediaStore.Images.Media.DATA), null, null, null)
                    ?: return null
            cursor.use {
                if (it.moveToNext()) {
                    filePath = cursor.getString(it.getColumnIndex(MediaStore.Images.Media.DATA))
                }
            }
        } else if ("file".equals(uri.scheme, ignoreCase = true)) { // 从文件中选择
            filePath = uri.path
        }
        return filePath
    }

    /**
     * 修复照相图片方向
     *
     * @param context
     * @param bitmap     需要旋转的bitmap
     * @param pictureUri 原图Uri
     * @return
     */
    fun fixCameraPhotoOrientation(context: Context, bitmap: Bitmap, pictureUri: Uri): Bitmap {
        try {
            val cameraPhotoOrientation = getCameraPhotoOrientation(context, pictureUri)
            Timber.d("cameraPhotoOrientation: %s", cameraPhotoOrientation)
            return if (cameraPhotoOrientation == 0) {
                bitmap
            } else {
                val matrix = Matrix()
                matrix.postRotate(cameraPhotoOrientation.toFloat())
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
        return bitmap
    }

    /**
     * 获得相机照片方向
     *
     * @param context
     * @param pictureUri
     * @return
     */
    fun getCameraPhotoOrientation(context: Context, pictureUri: Uri): Int {
        var rotate = 0
        try {
            context.contentResolver.notifyChange(pictureUri, null)
            val exif = ExifInterface(getFilePath(context, pictureUri))
            val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL)
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_270 -> rotate = 270
                ExifInterface.ORIENTATION_ROTATE_180 -> rotate = 180
                ExifInterface.ORIENTATION_ROTATE_90 -> rotate = 90
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
        return rotate
    }

    /**
     * 压缩图片可选参数
     */
    class CompressOptions(var maxWidth: Int = MAX_WIDTH,
                          var maxHeight: Int = MAX_HEIGHT,
                          var maxSize: Int = MAX_SIZE,
                          var format: Bitmap.CompressFormat = DEFAULT_FORMAT,
                          var quality: Int = DEFAULT_QUALITY) {

        companion object {
            /**
             * 最大宽度
             */
            const val MAX_WIDTH = 960
            /**
             * 最大高度
             */
            const val MAX_HEIGHT = 1280
            /**
             * 最大大小, 单位kb
             */
            const val MAX_SIZE = 250
            /**
             * 默认压缩格式
             */
            val DEFAULT_FORMAT = Bitmap.CompressFormat.JPEG
            /**
             * 默认质量
             */
            const val DEFAULT_QUALITY = 100
        }

    }
}