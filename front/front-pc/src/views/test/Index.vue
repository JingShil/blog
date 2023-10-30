<template>
  <div class="test-header">
    <div class="test-menu">
      <div class="test-menu-item">
        <a href="/login">博文</a>
      </div>
      <div class="test-menu-item">
        <router-link to="/blog">关注</router-link>
      </div>
      <div class="test-menu-item">
        <router-link to="/test">历史记录</router-link>
      </div>
    </div>
  </div>
  <div class="test-main" >
    <router-view></router-view>
  </div>
</template>

<script setup>

</script>

<style src="../../assets/css/test.css" scoped>

</style>