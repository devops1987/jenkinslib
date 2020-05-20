#!groovy

//声明引用的library，名字同在Jenkins系统配置下的 Global Pipeline Libraries 的 name
@Library('jenkinslib'@master) _
def tools = new org.devops.tools()   //同GitHub上仓库层级目录
def build = new org.devops.build()

//引用vars下的变量
hello()

//Global 
String workspace = "/opt/jenkins/workspace"
String buildType = "${env.buildType}"   //jenkins 参数化构建中定义了buildType
String buildShell = "${env.buildShell}"   //jenkins 参数化构建中定义了buildShell

//Pipeline
pipeline {
	agent { node { label "master" //指定运行节点的标签或者名称
                   customWorkspace "${workspace}" //指定运行工作目录（可选）
	    }
	}
	
	parameters { string(name: 'DEPLOY_ENV', defaultValue: 'staging', description: '')
	             booleanParam(name: 'DEBUG_BUILD', defaultValue: true, description: '')
	}
	//parameters { booleanParam(name: 'DEBUG_BUILD', defaultValue: true, description: '') }

	options {
	    timestamps() //日志会有时间戳
	    skipDefaultCheckout() //删除隐式checkout scm语句
	    disableConcurrentBuilds() //禁止并行（根据实际情况而定）
	    timeout(time: 1, unit: 'HOURS') //流水线超时设置1h
	}

	stages {
	    //下载代码
	    stage("GetCode"){ //阶段名称
	        when { environment name: 'DEPLOY_ENV', value: 'staging' }
	        steps{ //步骤
	            input id: 'PullCode', message: '是否下载代码？', ok: '是，继续！', parameters: [choice(choices: ['yes', 'no'], description: '', name: 'pullCode')], submitter: 'devops'
	            timeout(time: 5, unit: "MINUTES"){ //步骤超时时间
	                script{ //填写运行代码
	                    tools.PrintMsg("开始拉取代码",'red')
	                    println('获取代码')
	                }
	            }
	        }
	    }

	    //构建
	    stage("Build"){ //阶段名称
	        steps{ //步骤
	            timeout(time: 20, unit: "MINUTES"){ //步骤超时时间
	                script{ //填写运行代码
	                    tools.PrintMsg("开始应用打包",'green')
	                    println('应用打包')
	                    
	                    //mvnHome = tool "mvn3"
	                    //println(mvnHome)
			    //sh "${mvnHome}/bin/mvn ${buildShell}"
			    build.Build(buildType,buildShell)
	                }
	            }
	        }
	    }

	    //代码扫描
	    stage("CodeScan"){ //阶段名称
	        steps{ //步骤
	            timeout(time: 30, unit: "MINUTES"){ //步骤超时时间
	                script{ //填写运行代码
	                    tools.PrintMsg("开始代码扫描",'blue')
	                    println('代码扫描')
	                }
	            }
	        }
	    }
	}

    //构建后操作
    post {
        always {
            script{
                println("always")
            }
        }

        success {
            script{
                currentBuild.description += "\n 构建成功!"
            }
        }

        failure {
            script{
                currentBuild.description += "\n 构建失败!"
            }
        }

        aborted {
            script{
                currentBuild.description += "\n 构建取消!"
            }
        }
    }

}
