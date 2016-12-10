Android P2P Sample
===

该项目使用IJKPlayer，演示我们的P2P功能的样例程序

### 功能

- 直播、点播基本功能+P2P
- 直播时移支持
- HLS P2P原生支持
- 防盗播支持

### 开发者中心

开发者中心可帮助您查看一些统计数据。

**地址**: http://devcenter.vbyte.cn  
**测试帐号**: 用户名: test@vbyte.cn，密码: Vb360  

### 编译安装

[Android SDK][3]托管于第三方平台[Jcenter][5]上，使用上非常简单，此demo演示集成[Android SDK][3]是如此的简单。

如果想跳过编译，也可直接下载[demo.apk][6]到手机上直接安装。

- 将项目clone到本地
```bash
git clone https://github.com/Vbytes/android-sample.git
```

- 用AndroidStudio打开刚刚clone下来的项目
- 待gradle准备好后，即可编译安装到设备体验

**提示**: 如果您的android项目是一个Eclipse+ADT项目，我们建议您尽快转到AndroidStudio上面，当然，你也可以按照我们的[集成指南][7]手动将这些so库和jar包放到您的项目中。

**提示**: 最新版的ijkplayer-android上面实现低延迟秒开的优化是设置下面这个参数:
```java
ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "probesize", 4096);
IjkMediaPlayer.setOption(ijkMediaPlayer.OPT_CATEGORY_PLAYER, "packet-buffering", 0);
```

### 扩展链接

* **[Android SDK][3]**: SDK的Github地址，以及相应文档
* **[AndroidSample][4]**: 一个使用ijkplayer的简单样例
* **[API Doc][2]**: 更加详细的API文档，其中包含如直播时移的高级功能
* **[jcenter][5]**: Android SDK在jcenter上的位置
* **[demo下载][6]**: 此即为AndroidSample已编译完成的版本，里面的内容属于[开发者中心][1]测试帐号的，欢迎试用

### 技术支持

感谢阅读本篇文档，希望能帮您尽快上手Android SDK的用法，再次欢迎您使用月光石P2P加速SDK！

*温馨提示*：如果你需要任何帮助，或有任何疑问，请[联系我们](mailto:contact@exatech.cn)。

[1]: http://devcenter.vbyte.cn
[2]: http://docs.vbyte.cn/api/android/
[3]: https://github.com/Vbytes/libp2pimpl-android
[4]: https://github.com/Vbytes/android-sample
[5]: https://bintray.com/vbyte/maven/libp2pimpl
[6]: http://data1.vbyte.cn/apk/vbyte-demo.20161031.apk
[7]: http://data1.vbyte.cn/pkg/20160921.tar.gz
[8]: http://docs.vbyte.cn/api/android/#eclipse
[9]: https://bintray.com/

