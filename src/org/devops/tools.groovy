package org.devops

//格式化日志输出字体颜色
def PrintMsg(value,color){
    colors = [
        'red': "\033[40;31m >>>>>>>>>>>> ${value} <<<<<<<<<<<< \033[0m",
        'blue': "\033[47;34m ++++++++++++ ${value} ++++++++++++ \033[0m",
        'green': "[1;32m >>>>>>>>>>>> ${value} <<<<<<<<<<<< [m",
        'green1': "\033[40;32m >>>>>>>>>>>> ${value} <<<<<<<<<<<< \033[0m"
    ]
    ansiColor('xterm'){//需要安装 ansiColor 插件
        println(colors[color])
    }
}
