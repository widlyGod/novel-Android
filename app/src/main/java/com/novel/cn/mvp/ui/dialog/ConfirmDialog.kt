package com.novel.cn.mvp.ui.dialog

import android.app.Dialog
import android.content.Context
import com.flyco.dialog.listener.OnBtnClickL
import com.flyco.dialog.widget.NormalDialog

/**
 * Created by jmw on 2018/12/21
 */
class ConfirmDialog(context: Context, init: (ConfirmDialogBuilder.() -> Unit)) : NormalDialog(context) {


    init {
        apply {
            ConfirmDialogBuilder().apply {
                init()

                style(STYLE_TWO)//
                        .titleTextSize(23f)//


                setOnBtnClickL(OnBtnClickL {
                    onCancel?.invoke(this@ConfirmDialog)
                    dismiss()
                }, OnBtnClickL {
                    onConfirm?.invoke(this@ConfirmDialog)
                    dismiss()
                })
            }
        }
    }

    fun show(content: String) {
        content(content)
        super.show()
    }

    class ConfirmDialogBuilder(
            var content: CharSequence? = null,
            var contentRes: Int? = null,
            var onConfirm: (Dialog.() -> Unit)? = {
                dismiss()
            },
            var onCancel: (Dialog.() -> Unit)? = {
                dismiss()
            },
            var cancelable: Boolean = true,
            var canceledOnTouchOutside: Boolean = true
    )
}