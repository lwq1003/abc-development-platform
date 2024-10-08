<template>
	<view class="normal-login-container">
		<view class="logo-content align-center justify-center flex">
			<image style="width: 80rpx;height: 80rpx;" :src="globalConfig.appInfo.logo" mode="widthFix">
			</image>
			<text class="title">系统登录</text>
		</view>
		<view class="login-form-content">
			<view class="input-item flex align-center">
				<view class="iconfont icon-user icon"></view>
				<input v-model="loginForm.username" class="input" type="text" placeholder="请输入账号" maxlength="30" />
			</view>
			<view class="input-item flex align-center">
				<view class="iconfont icon-password icon"></view>
				<input v-model="loginForm.password" type="password" class="input" placeholder="请输入密码" maxlength="20" />
			</view>
			<view class="action-btn">
				<button @click="handleLogin" class="login-btn cu-btn block bg-blue lg round">登录</button>
			</view>
			<view class="reg text-center" v-if="register">
				<text class="text-grey1">没有账号？</text>
				<text @click="handleUserRegister" class="text-blue">立即注册</text>
			</view>		
		</view>

	</view>
</template>

<script>
	import {
		getToken
	
	} from '@/utils/auth'
	export default {
		data() {
			return {
				codeUrl: "",
				captchaEnabled: true,
				// 用户注册开关
				register: true,
				globalConfig: getApp().globalData.config,
				loginForm: {				
					username: "",
					password: ""
				}
			}
		},
		created() {			
			const token=getToken()
			if(token && token!=''){
				this.$tab.reLaunch('/pages/index')
			}
		},
		methods: {
			// 用户注册
			handleUserRegister() {
				this.$tab.redirectTo(`/pages/register`)
			},
			// 隐私协议
			handlePrivacy() {
				let site = this.globalConfig.appInfo.agreements[0]
				this.$tab.navigateTo(`/pages/common/webview/index?title=${site.title}&url=${site.url}`)
			},
			// 用户协议
			handleUserAgrement() {
				let site = this.globalConfig.appInfo.agreements[1]
				this.$tab.navigateTo(`/pages/common/webview/index?title=${site.title}&url=${site.url}`)
			},

			// 登录方法
			async handleLogin() {
				if (this.loginForm.username === "") {
					this.$modal.msgError("请输入您的账号")
				} else if (this.loginForm.password === "") {
					this.$modal.msgError("请输入您的密码")
				} else {
					this.$modal.loading("登录中，请耐心等待...")
					this.pwdLogin()
				}
			},
			// 密码登录
			async pwdLogin() {
				this.$store.dispatch('Login', this.loginForm).then((res) => {
					this.$modal.closeLoading()
					this.$tab.reLaunch('/pages/index')
				})
			}
		}
	}
</script>

<style lang="scss">
	page {
		background-color: #ffffff;
	}

	.normal-login-container {
		width: 100%;

		.logo-content {
			width: 100%;
			font-size: 21px;
			text-align: center;
			padding-top: 15%;

			image {
				border-radius: 4px;
			}

			.title {
				margin-left: 10px;
			}
		}

		.login-form-content {
			text-align: center;
			margin: 20px auto;
			margin-top: 15%;
			width: 80%;

			.input-item {
				margin: 20px auto;
				background-color: #f5f6f7;
				height: 45px;
				border-radius: 20px;

				.icon {
					font-size: 38rpx;
					margin-left: 10px;
					color: #999;
				}

				.input {
					width: 100%;
					font-size: 14px;
					line-height: 20px;
					text-align: left;
					padding-left: 15px;
				}

			}

			.login-btn {
				margin-top: 40px;
				height: 45px;
			}

			.reg {
				margin-top: 15px;
			}

			.xieyi {
				color: #333;
				margin-top: 20px;
			}

			.login-code {
				height: 38px;
				float: right;

				.login-code-img {
					height: 38px;
					position: absolute;
					margin-left: 10px;
					width: 200rpx;
				}
			}
		}
	}
</style>