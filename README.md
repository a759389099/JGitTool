# JGitTool
功能:获取某次修改和新增的项目文件




首先需要额外检出项目到一个文件夹,且不能修改该文件夹





1. 修改JGitConstant中的3个常量
gitRoot :你额外检出项目(仓库)的所在url
revision:该次提交的commitid,长id
destPath:要保存到的代码文件夹所在位置


2.运行startcheckout类的主方法
