<template>
	<view class="normal-login-container">
		<view class="logo-content align-center justify-center flex">
			<image style="width: 80rpx;height: 80rpx;" :src="globalConfig.appInfo.logo" mode="widthFix">
			</image>
			<text class="title">注册</text>
		</view>
		<view class="login-form-content">
			<view class="input-item flex align-center">
				<view class="iconfont icon-user icon"></view>
				<input v-model="registerForm.account" class="input" type="text" placeholder="请输入账号" maxlength="30" />
			</view>
			<view class="input-item flex align-center">
				<view class="iconfont icon-user icon"></view>
				<input v-model="registerForm.name" class="input" type="text" placeholder="请输入昵称" maxlength="30" />
			</view>
			<view class="input-item flex align-center">
				<view class="iconfont icon-password icon"></view>
				<input v-model="registerForm.password" type="password" class="input" placeholder="请输入密码,至少8位"
					maxlength="20" />
			</view>
			<view class="input-item flex align-center">
				<view class="iconfont icon-password icon"></view>
				<input v-model="registerForm.confirmPassword" type="password" class="input" placeholder="请输入重复密码"
					maxlength="20" />
			</view>
			<view class="input-item flex align-center">
				<view class="iconfont icon-code icon"></view>
				<input v-model="registerForm.email" type="text" class="input" placeholder="请输入邮箱" />

			</view>
			<view class="action-btn">
				<button @click="handleRegister()" class="register-btn cu-btn block bg-blue lg round">注册</button>
			</view>
		</view>
		<view class="xieyi text-center">
			<text @click="handleUserLogin" class="text-blue">使用已有账号登录</text>
		</view>
	</view>
</template>

<script>
	import {
		register
	} from '@/api/login'

	export default {
		data() {
			return {
				globalConfig: getApp().globalData.config,
				registerForm: {
					account: "",
					name: "",
					password: "",
					confirmPassword: "",
					code: "",
					uuid: ''
				}
			}
		},

		methods: {
			// 用户登录
			handleUserLogin() {
				this.$tab.navigateTo(`/pages/login`)
			},

			// 注册方法
			async handleRegister() {
				if (this.registerForm.account === "") {
					this.$modal.msgError("请输入您的账号")
				} else if (this.registerForm.name === "") {
					this.$modal.msgError("请输入您的昵称")
				} 
				else if (this.registerForm.password === "") {
					this.$modal.msgError("请输入您的密码")
				} else if (this.registerForm.confirmPassword === "") {
					this.$modal.msgError("请再次输入您的密码")
				} else if (this.registerForm.password !== this.registerForm.confirmPassword) {
					this.$modal.msgError("两次输入的密码不一致")
				} else if (this.registerForm.email === "") {
					this.$modal.msgError("请输入邮箱")
				} else {
					this.$modal.loading("注册中，请耐心等待...")
					this.register()
				}
			},
			// 用户注册
			async register() {
				register(this.registerForm).then(res => {
					this.$modal.closeLoading()
					uni.showModal({
						title: "系统提示",
						content: "恭喜你，您的账号 " + this.registerForm.account + " 注册成功！",
						success: function(res) {
							if (res.confirm) {
								uni.redirectTo({
									url: `/pages/login`
								});
							}
						}
					})
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

			.register-btn {
				margin-top: 40px;
				height: 45px;
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