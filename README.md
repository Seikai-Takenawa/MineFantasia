
我的音乐幻想|MineFantasia
=======
![MineFantasia](src/main/resources/icon.png)
<p align="center">
  <strong>语言</strong>: <a href="README-en.md">English</a> | <a href="README.md">简体中文</a>
</p>


介绍
=======
欢迎来到我的音乐幻想！这是一个我的世界音乐模组。<br>

模组目前仍处于开发期，但你可以下载模组体验当前开发完成的功能。<br>

模组提供了钢琴、各种合成器、弦乐、管乐等各种乐器。<br>
只需要右键对应的乐器，你就可以在打开的新界面中使用键盘或鼠标开始乐器的演奏了。<br>

除了乐器，模组也提供了多人共同演奏系统。这意味着你可以与你的好友一同演奏一场华丽的乐器合奏。<br>

除此之外，为了适配乐器演奏动画，模组通过最新的GeckoLib5，替换了原版Minecraft中，所有视角的玩家模型。
在你演奏乐器的过程中，模型也会做出对应的演奏动作。<br>

自定义玩家模型
=======
为了让模组的乐器演奏系统更加生动，模组添加了基于GeckoLib5的动画系统，并将玩家模型替换为了GeckoLib模型。这也使得玩家第一视角为真正的第一视角。<br>

模型支持替换，但ysm模型或其它模组的模型在此模组中<strong>不受支持</strong>。你应该使用GeckoLib模型。<br>

在你初次运行模组并进入世界后，会在你的`mods`文件夹下生成模组的专属文件夹。<br>

其文件结构为：<br>
<details style="background-color: #a9a9a933; border-radius: 8px; padding: 15px; border: 1px solid #e1e4e8;">
<summary style="cursor: pointer; font-weight: bold; color: #ffffffcc; font-size: 1.1em;">
 点击展开
</summary>

📁`minefantasia`<br>
├──📁`model`<br>
│ ├──📄`player.geo.json`<br>
├──📁`animation`<br>
│ ├──📄`player.animation.json`<br>
├──📁`texture`<br>
│ └──🖼️`player.png`<br>
└──📄`uuid.json`<br>

</details>

其中，`model`文件夹存放你所有的GeckoLib模型文件`key.geo.json`，`animation`文件夹存放你所有的GeckoLib模型动画文件`key.animation.json`，
`texture`文件夹存放你所有的GeckoLib模型贴图`key.png`。<br>

每个目录都将生成一个以`player`为`key`的默认JSON文件，你不应该移除或替换它们。<br>

`uuid.json`文件是一个以玩家uuid命名的文件，只在玩家进入世界时生成，其内容含有四个关键记录字段：<br>
<details style="background-color: #a9a9a933; border-radius: 8px; padding: 15px; border: 1px solid #e1e4e8;">
<summary style="cursor: pointer; font-weight: bold; color: #ffffffcc; font-size: 1.1em;">
 点击展开
</summary>

📄`uuid.json`<br>
├── 🗝️`key`字段：需要为独特的、唯一的字段，用于在模组代码中注册、标记和绑定对应玩家模型的id<br>
├── 🗝️`model`字段：自定义GeckoLib玩家模型文件的绝对路径，<strong>不需要</strong>加上`.geo.json`后缀。<br>
├── 🗝️`texture`字段：自定义玩家模型贴图的绝对路径，需要完整的文件名称。<br>
└── 🗝️`animation`字段: 自定义GeckoLib玩家模型动画文件的绝对路径，<strong>不需要</strong>加上`.animation.json`后缀.<br>

</details>

为了便于模组内部进行模型注册和区分，所有模型文件的文件名和骨骼需要加上你的模型`key`作为前缀，如：`key.geo.json`，`key.head`等。

模组对模型骨骼数量及其结构并没有特别严格的规定和限制，但请确保在你的自定义GeckoLib模型中包含符合如下要求的重要骨骼：<br>
<details style="background-color: #a9a9a933; border-radius: 8px; padding: 15px; border: 1px solid #e1e4e8;">
<summary style="cursor: pointer; font-weight: bold; color: #ffffffcc; font-size: 1.1em;">
 点击展开
</summary>

📄`key.geo.json`<br>
├── 🦴`key.head`骨骼：此骨骼用于模组内部计算第一人称视角下相机的坐标及偏移；同时定位模型头部位置并进行头部随视角旋转的计算。<br>
├── 🦴`key.cameraAnchor`骨骼：此骨骼下只能为一个定位器（`locator`），定位器的名称<strong>必须</strong>为cameraAnchor，且其父类<strong>必须</strong>为模型根骨骼（key.root）。此骨骼用于模组内部计算第一人称视角下的相机坐标及偏移。<br>
├── 🦴`key.rightHandItem`骨骼：此骨骼下只能为一个定位器（`locator`），定位器的名称<strong>必须</strong>为rightHandItem,其父类需为对应手部骨骼，以允许物品随动画而移动。此骨骼用于模组内部计算所有视角下玩家右手（主手）物品的渲染位置和偏移。<br>
└── 🦴`key.leftHandItem`骨骼：此骨骼下只能为一个定位器（`locator`），定位器的名称<strong>必须</strong>为leftHandItem,其父类需为对应手部骨骼，以允许物品随动画而移动。此骨骼用于模组内部计算所有视角下玩家左手（副手）物品的渲染位置和偏移。<br>

</details>

模组使用以上四个骨骼的`pivot`用于坐标计算，请确保它们的pivot数值正确。<br>
对于其它骨骼的子骨骼、命名等，模组并没有严格限制。<br>

需要注意的是，模组目前的玩家模型替换系统在多人游戏下并不会自动同步各个玩家正在使用的模型，且依然需要手动调整`uuid.json`中的内容来进行模型的更换。这意味着如果你想向其他人展示自己的模型，你需要将你的模型的所有json文件发送给他们，并通知他们手动修改<strong>他们设备上的</strong>对应的你的`uuid.json`。<br>

自定义模型动画
=======
模组支持自定义玩家模型动画。由于所有动画名称硬编码在模组代码中，所以你的自定义模型动画名称需与模组编码的动画名称相同。<br>
当前支持的动画及其命名要求如下：<br>
<details style="background-color: #a9a9a933; border-radius: 8px; padding: 15px; border: 1px solid #e1e4e8;">
<summary style="cursor: pointer; font-weight: bold; color: #ffffffcc; font-size: 1.1em;">
 点击展开
</summary>

📄`key.animation.json`<br>
├── 🎬`key.walk`：玩家模型在行走时播放的动画。<br>
├── 🎬`key.idle`：玩家模型在静止时播放的动画。<br>
└── 🎬`key.swim`：玩家模型在游泳时播放的动画。<br>

</details>

后期也会适配更多的动画。


安装
=======
1.请前往GeckoLib的[GitHub仓库](https://github.com/bernie-g/geckolib)、[modrinth](https://modrinth.com/mod/geckolib)等位置下载GeckoLib-NeoForge对应版本，并将下载的jar包放入你的'mods'文件夹下。<br>
2.请于右侧的release或modrinth等位置下载本模组的最新版本，并将下载的jar包放入你的'mods'文件夹下。<br>

常见问题
=======
Q1.我该如何获取乐器？<br>
A1.目前，乐器暂不支持合成和制造。非创造模式下，模组的钢琴自然生成于模组结构音乐厅（Concert Hall）的中心位置且不可破坏，
其它的乐器仅能够在音乐厅的后台箱子里获取。<br>

Q2.在多人游戏下，我没法听到其它玩家演奏乐器的声音。<br>
A2.多人游戏下，乐器演奏系统只会将演奏信息同步到与正在演奏乐器的那一位玩家处于同一区块内的玩家。<br>

Q3.我在第一人称视角下能够穿过方块进行透视。<br>
A3.这通常是由于建模师在设置模型的`cameraAnchor`骨骼时超过了头部骨骼界限，导致模组内部计算时相机的Z轴坐标向玩家正前方偏移过大。后期会优化模组这方面的算法。目前此问题可以尝试修改对应模型的'geo.json'中的cameraAnchor骨骼的Z坐标来改善。<br>

Q4.各个视角下玩家手部显示某些物品的大小、角度等很奇怪。<br>
A4.由于替换了整个原版的玩家模型系统，使得原版的物品渲染逻辑已经不再适用于替换的GeckoLib模型。此问题正在进行适配和改善。<br>

Q5.将来会有Forge/Fabric端的模组吗？<br>
A5.个人开发精力有限，暂没有Forge/Fabric端的开发计划。

Q6.我能获取源码向模组中添加新的自定义功能吗？<br>
A6.模组暂不开源，感谢谅解！如果你有将模组移植到其它mod加载器的想法，请发送邮件到`morinoyoake@qq.com`。


