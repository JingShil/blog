<template>
	<div class="layout-body">
		<div class="layout-header">
			<h1>哈哈</h1>
			<div class="layout-menu">
				<el-menu 
					:default-active="activeIndex"
					class="el-menu-demo"
					mode="horizontal"
					:ellipsis="false"
					@select="handleSelect">
					<el-menu-item index="1">
						<!-- <router-link to="/blog" style="text-decoration: none;">博文</router-link> -->
						<a href="/blog"></a>
					</el-menu-item>
					<el-menu-item index="2">
						<router-link to="/test" style="text-decoration: none;">关注</router-link>
					</el-menu-item>
					<el-menu-item index="3">
						<router-link to="/" style="text-decoration: none;">历史记录</router-link>
					</el-menu-item>
				</el-menu>
			</div>
			<div class="layout-search" style="display: flex;">
				<el-input v-model="input" placeholder="Please input"/>
				<div class="layout-search-button"  style="display: flex;">
					<el-button type="primary">
						<el-icon :size="size" :color="color">
							<Search />
						</el-icon>
					</el-button>
				</div>
			</div>
			<div class="layout-avatar">
				<el-dropdown>
					<el-avatar>
						<el-icon><User /></el-icon>	
					</el-avatar>
					<template #dropdown>
						<el-dropdown-menu>
							<el-dropdown-item>个人中心</el-dropdown-item>
							<el-dropdown-item>关注</el-dropdown-item>
							<el-dropdown-item>粉丝</el-dropdown-item>
							<el-dropdown-item>退出登陆</el-dropdown-item>
						</el-dropdown-menu>
					</template>
				</el-dropdown>
			</div>
		</div>
		<div class="layout-container">
			<div class="layout-main" >
			
			<router-view></router-view>
		</div>
		</div>
		
	</div>
</template>

<script>
import { defineComponent, ref } from 'vue';


export default defineComponent({
  components: {
    
  },
  setup() {
    const count = ref(20);
    const load = () => {
      count.value += 1;
    };
    return {
      count,
      load
    };
  }
});
</script>

<style src="../../assets/css/layout.css" scoped>
</style>
