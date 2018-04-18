package com.nelson.glidetest.glidemodule;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.nelson.glidetest.network.UnsafeOkHttpUrlLoader;
import java.io.InputStream;

/**
 * 自定义Module实例：接受自签名证书的HTTPS
 *
 * 这个类的细节，应该对于这个Glide系统应该有一个大概的理解，Glide能去替换内部的工厂组件
 *
 * Created by Nelson on 16/12/19.
 */
public class UnsafeOkHttpGlideModule implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

    }

    @Override
    public void registerComponents(Context context, Glide glide) {

        /**
         * registerComponents()去设置不同的网络库
         * 默认情况下，Glide内部使用了标准的HttpUrlConnection去下载图片，Glide也提供了两个集合库，这三个都一样非常严格的安全设置，
         * 唯一的缺点可能是当你的图片从服务器获取时，是使用HTTPS，且是自签名的(self-signed)，这时Glide不会下载或显示图片了，
         * 因为自签名的证书被认为是一个安全的问题。因此，我们需要去实现自己的网络栈，它接受自签名证书，幸运的是，我们之前已经实现了一个"不安全"
         * 的OkHttpClient(UnsafeHttpClient).
         *
         * 我们的优势是，OkHttp整合库为Glide做了几乎相同的事情，所以我们可以跟着他们走。首先，我们需要在GlideModule中声明我们的定制，
         * 正如你所期待的，我们要在registerComponents()方法中去做适配，可以调用.register()方法去改变Glide的基本部件。
         * 步骤：
         * 1.Glide使用一个GlideLoader去链接数据到一个具体的数据类型，在我们的实例中，我们要去创建一个ModelLoader，连接传入的URL；
         * 2.通过GlideUrl类来代表一个InputStream;
         * 3.Glide要能创建一新的ModelLoader，需要在.register()方法中传递一个工厂;
         */
        glide.register(GlideUrl.class, InputStream.class, new UnsafeOkHttpUrlLoader.Factory());
    }
}
