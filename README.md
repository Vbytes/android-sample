Android P2P Sample
===

该项目使用IJKPlayer，演示我们的P2P功能的样例程序

## 开发者中心

开发者中心可帮助您查看一些统计数据。

**地址**: http://devcenter.vbyte.cn  
**测试帐号**: 用户名: test@vbyte.cn，密码: Vb360  

## 集成指南

- 我们的库还没有经过jcenter的审核，暂时先在项目根build.gradle文件里面更改
```
allprojects {
    repositories {
        jcenter()
        maven {
            url 'http://dl.bintray.com/vbyte/maven'  // 暂时的，等jcenter审核通过可取消此配置
        }
    }
}

```
- 添加P2P和IJKPlayer的支持。在您的项目module中的build.gradle添加依赖如下：
```
dependencies {
    compile fileTree(include: ['*.jar'], dir: 'libs')

    compile 'tv.danmaku.ijk.media:ijkplayer-java:0.5.1'
    compile 'tv.danmaku.ijk.media:ijkplayer-armv7a:0.5.1'
    // ExoPlayer as IMediaPlayer: optional, experimental
    compile 'tv.danmaku.ijk.media:ijkplayer-exo:0.5.1'
    compile 'cn.vbyte.p2p:libp2p:1.0.0'
    compile 'cn.vbyte.p2p:libadvancep2p:1.0.2'
}
```
- 然后就可以尽情地使用IJKPlayer和我们的P2P带来的便利功能吧

**提示**: 如果您的android项目是一个Eclipse+ADT项目，我们建议您尽快转到AndroidStudio上面，当然，你也可以按照我们的文档手动将这些so库和jar包放到您的项目中。

**提示**: 最新版的ijkplayer-android上面实现低延迟秒开的优化是设置下面这个参数:
```java
ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "probesize", 4096);
```

## 功能列表

- [x] 低延迟P2P直播,进入应用->电视直播->安徽卫视 即是低延迟P2P 
- [x] HLS、MP4、FLV等格式的点播P2P  
- [ ] HLS直播P2P
