<template>
	<div class="layout-body">
		<div class="layout-header">
			<h1>哈哈</h1>
			<div class="layout-menu">
				<div class="layout-menu-item" @click="handMenuItemChangeStyle(0)">
					<router-link to="/">博文</router-link>
				</div>
				<div class="layout-menu-item" @click="handMenuItemChangeStyle(1)">
					<router-link to="/">关注</router-link>
				</div>
				<div class="layout-menu-item" @click="handMenuItemChangeStyle(2)">
					<router-link to="/">历史</router-link>
				</div>
			</div>
			<div class="layout-search" style="display: flex;">
				<input placeholder="请输入"/>
				<button>
					<el-icon><Search /></el-icon>
				</button>
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
  setup() {
    const count = ref(20);
    const activeHandIndex = ref(0);

    const load = () => {
      count.value += 1;
    };

    const handMenuItemChangeStyle = (index) => {
      let layout_menu_item = document.getElementsByClassName('layout-menu-item')[index];
      layout_menu_item.classList.add('layout-menu-item-focus');
			if(activeHandIndex.value != index){
				let layout_menu_item_before = document.getElementsByClassName('layout-menu-item')[activeHandIndex.value];
				layout_menu_item_before.classList.remove('layout-menu-item-focus');
			}
      activeHandIndex.value = index;
    };

    return {
      count,
      activeHandIndex,
      load,
      handMenuItemChangeStyle
    };
  }
});


</script>

<style src="../../assets/css/layout.css" scoped>
</style>
