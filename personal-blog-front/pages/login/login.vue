<template>
	<view class="body">
		<match-media min-width="600">
			<view class="container">
				<view class="form-box">
					<view class="register-box hidden">
						<h1>REGISTER</h1>
						<input type="text" placeholder="用户名" placeholder-class="input-placeholder" 
							v-model="user.name"/>
						<input type="password" placeholder="密码" placeholder-class="input-placeholder"
							v-model="user.password"/>
						<input type="password" placeholder="确认密码" placeholder-class="input-placeholder"
							v-model="user.passwordFlag"/>
						<input type="number" placeholder="手机号" placeholder-class="input-placeholder"
							v-model="user.phone"/>
						<input type="number" placeholder="验证码" placeholder-class="input-placeholder"
							v-model="user.code"/>
						<view class="code">
							<view class="code-left" @click="getCode(user.phone, 0)"><view>发送验证码</view></view>
						</view>
						<button id="register" @click="register(user)">注册</button>
					</view>
					<view class="login-box">
						<h1>LOGIN</h1>
						<input type="number" placeholder="手机号" placeholder-class="input-placeholder" v-model="user.phone"/>
						<input type="password" v-if="codeFlag==false" placeholder="密码" 
							placeholder-class="input-placeholder" v-model="user.password"/>
						<input type="number" v-if="codeFlag==true" placeholder="验证码" 
							placeholder-class="input-placeholder" v-model="user.code"/>
						<view class="code">
							<view class="code-left" v-if="codeFlag==false" @click="codeFlag=true, user.password='' "><view>验证码登陆</view></view>
							<view class="code-left" v-if="codeFlag==true" @click="codeFlag=false, user.code='' "><view>密码登陆</view></view>
							<view class="code-right" v-if="codeFlag==true" @click="getCode(user.phone, 1)"><view>发送验证码</view></view>
						</view>
						
						<button id="login" @click="login(user)">登陆</button>
					</view>
				</view>
				<view class="con-box left">
					<h2>欢迎来到<span>个人博客</span></h2>
					<img src="@/static/c1.png"/>
					<h2>已有账号</h2>
					<button id="login" @click="handleToLoginClick(), user.phone=''">去登陆</button>
				</view>
				<view class="con-box right">
					<h2>欢迎来到<span>个人博客</span></h2>
					<img src="@/static/c1.png"/>
					<h2>没有账号?</h2>
					<button id="register" @click="handleToRegisterClick(), user.phone=''">去注册</button>
				</view>
			</view>
		</match-media>
		
	</view>
</template>

<script>
import {login} from '../../api/login.js'
import {register} from '../../api/login.js'
import {getCode} from '../../api/login.js'

export default{
	data() {
		return {
			codeFlag: false,
			user: {
				"name" : "",
				"password" : "",
				"passwordFlag" : "",
				"phone" : "",
				"code" : ""
			}
		}
	},
	methods: {
		handleToLoginClick() {
		  
			let form_box=document.getElementsByClassName('form-box')[0];
			let register_box=document.getElementsByClassName('register-box')[0];
			let login_box=document.getElementsByClassName('login-box')[0];
			console.log("登陆界面");
			  
			form_box.style.transform='translateX(0%)'
			register_box.classList.add('hidden');
			login_box.classList.remove('hidden');
		},
		handleToRegisterClick() {
			let form_box=document.getElementsByClassName('form-box')[0];
			let register_box=document.getElementsByClassName('register-box')[0];
			let login_box=document.getElementsByClassName('login-box')[0];
			console.log("注册界面");
			
			form_box.style.transform='translateX(250px)'
			login_box.classList.add('hidden');
			register_box.classList.remove('hidden');
		},
		login(user) {
			console.log(user)
			login(user).then(res => {
				console.log(res.data)
				if(res.data.code==0) {
					uni.showToast({
						title: res.data.msg,
						//将值设置为 success 或者直接不用写icon这个参数
						icon: 'error',
						//显示持续时间为 2秒
						duration: 2000
					})  
				}else {
					uni.showToast({
						title: res.data.data,
						//将值设置为 success 或者直接不用写icon这个参数
						icon: 'success',
						//显示持续时间为 2秒
						duration: 2000
					})  
					uni.setStorageSync('token', res.data.token)
				}
			}).catch(err => {
				uni.showToast({
					title: err,
					//将值设置为 success 或者直接不用写icon这个参数
					icon: 'error',
					//显示持续时间为 2秒
					duration: 2000
				})  
			})
		},
		getCode(phone, loginFlag) {
			console.log(phone)
			console.log(loginFlag)
			getCode(phone, loginFlag).then(res => {
				console.log(res.data.data);
				uni.get
			}).catch(err => {
				
			})
		},
		register(user) {
			console.log(user)
			register(user).then(res => {
				console.log(res.data)
				if(res.data.code==0) {
					uni.showToast({
						title: res.data.msg,
						//将值设置为 success 或者直接不用写icon这个参数
						icon: 'error',
						//显示持续时间为 2秒
						duration: 2000
					})  
				}else {
					uni.showToast({
						title: res.data.data,
						//将值设置为 success 或者直接不用写icon这个参数
						icon: 'success',
						//显示持续时间为 2秒
						duration: 2000
					})  
					uni.setStorageSync('token', res.data.token)
				}
			}).catch(err => {
				uni.showToast({
					title: err,
					//将值设置为 success 或者直接不用写icon这个参数
					icon: 'error',
					//显示持续时间为 2秒
					duration: 2000
				})  
			})
		},
		
		
		
	}
}
</script>

<style>
@import '@/common/css/login.css';

</style>