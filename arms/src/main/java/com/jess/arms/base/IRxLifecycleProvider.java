package com.jess.arms.base;

import io.reactivex.disposables.Disposable;

public interface IRxLifecycleProvider {

    /**
     * 绑定到生命周期
     */

    void bindToLifecycle(Disposable disposable);

    void removeFromLifecycle(Disposable disposable);


}
