<template>
  <div class="body">
    <div class="container">
      <div class="form-box">
        <div class="login-layout">
          <h1>LOGIN</h1>
          <div class="input-wrapper">
            <label>手机号</label>
            <input type="tel" placeholder="手机号" v-model="user.phone" />
          </div>
          <div class="input-wrapper"  v-if="passwordFlag==true">
            <label>密码</label>
            <input class="password" type="password" placeholder="密码" v-model="user.password"/>
            <div class="conceal-password" id='conceal' @click="handleGetOrHiddenPassword(0, !hiddenFlag1), hiddenFlag1=!hiddenFlag1"></div>
          </div>
          <div class="input-wrapper"  v-if="passwordFlag==false">
            <label>验证码</label>
            <input type="tel" placeholder="验证码" v-model="user.code"/>
          </div>
          <div class="link">
            <div class="login-method" v-if="passwordFlag==true" @click="passwordFlag=false, reset()">验证码登陆</div>
            <div class="login-method" v-if="passwordFlag==false" @click="passwordFlag=true, reset()">密码登陆</div>
            <div class="send-code" v-if="passwordFlag==false" @click="getCode(user, 1)">发送验证码</div>
          </div>
          <button @click="login(user, passwordFlag)">登陆</button>
        </div>
        <div class="register-layout hidden">
          <h1>REGISTER</h1>
          <div class="input-wrapper">
            <label>用户名</label>
            <input type="text" placeholder="用户名" v-model="user.name"/>
          </div>
          <div class="input-wrapper">
            <label>手机号</label>
            <input type="tel" placeholder="手机号" v-model="user.phone"/>
          </div>
          <div class="input-wrapper">
            <label>密码</label>
            <input class="password" type="password" placeholder="密码" v-model="user.password"/>
            <div class="conceal-password" id='conceal' @click="handleGetOrHiddenPassword(1, !hiddenFlag2), hiddenFlag2=!hiddenFlag2"></div>
            
          </div>
          <div class="input-wrapper">
            <label>确认密码</label>
            <input class="password" type="password" placeholder="确认密码" v-model="confirmPassword"/>
            <div class="conceal-password" id='conceal' @click="handleGetOrHiddenPassword(2, !hiddenFlag3), hiddenFlag3=!hiddenFlag3"></div>
          </div>
          <div class="input-wrapper">
            <label>验证码</label>
            <input type="tel" placeholder="验证码" v-model="user.code"/>
          </div>
          <div class="link">
            <div class="send-code" @click="getCode(user, 0)">发送验证码</div>
          </div>
          <button @click="register(user)">注册</button>
        </div>
      </div>
      <div class="con-box">
        <h2>欢迎来到<span>学习博客</span></h2>
        <button v-if="loginFlag==true" @click="handleToRegisterClick(), reset()">去注册</button>
        <button v-if="loginFlag==false" @click="handleToLoginClick(), reset()">去登陆</button>
      </div>
    </div>
  </div>
</template>


<script>
import {getCode, login, register} from '@/api/login'
import { ElMessageBox } from 'element-plus';

export default({


  data(){

    return{
      passwordFlag: true,
      loginFlag: true,
      user: {
        "name": null,
        "phone": null,
        "password": null,
        "code": null,
      },
      confirmPassword: null,
      hiddenFlag1: true,
      hiddenFlag2: true,
      hiddenFlag3: true,
    }
  },
  methods:{
    handleToLoginClick(){
      let con_box=document.getElementsByClassName('con-box')[0];
      // con_box.remove('hidden')
      con_box.style.transform='translateX(0%)';
      this.loginFlag=true;
    },
    handleToRegisterClick(){
      let con_box=document.getElementsByClassName('con-box')[0];
      let width = con_box.offsetWidth;
      con_box.style.transform = `translateX(-${width}px)`;
      this.loginFlag=false;
    },
    
    handleGetOrHiddenPassword(index, hiddenFlag) {
      let password_icon=document.getElementsByClassName('conceal-password')[index];
      let password=document.getElementsByClassName('password')[index];
      if(hiddenFlag==false){
        password_icon.classList.add('conceal-show');
        password.setAttribute('type', 'text');
      }else {
        password_icon.classList.remove('conceal-show');
        password.setAttribute('type', 'password');
      }
    },
    reset() {
      this.user = {
        "name": null,
        "phone": null,
        "password": null,
        "code": null,
      },
      this.confirmPassword = null;
    },
    getCode(user, loginFlag) {
      user.loginFlag = loginFlag;
      getCode(user).then(res => {
        if(res.data.code==1)
          ElMessageBox.alert(res.data.data)
        else
          ElMessageBox.alert(res.data.msg)
        console.log(res.data.data);
      }).catch(err=>{
        ElMessageBox.alert(err)
      })
    },
    login(user, passwordFlag) {
      if(passwordFlag==false) {
        user.password=null;
      }else {
        user.code=null;
      }
      login(user).then(res => {
        if(res.data.code==1){
          ElMessageBox.alert(res.data.data)
          localStorage.setItem('token', res.data.token);
          console.log(localStorage.getItem('token'))
        }
        else
          ElMessageBox.alert(res.data.msg)
        console.log(res.data.data);
      }).catch(err=>{
        ElMessageBox.alert(err)
      })
    },
    register(user) {
      register(user).then(res => {
        if(res.data.code==1)
          ElMessageBox.alert(res.data.data)
        else
          ElMessageBox.alert(res.data.msg)
        console.log(res.data.data);
      }).catch(err=>{
        ElMessageBox.alert(err)
      })
    }
    
  }
})
</script>


<style src="../../assets/css/login.css" scoped>
/* @import url(../../assets/css/login.css); */
</style>