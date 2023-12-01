<template>
	<div class="blog-content" style="height: 100%;">
		<div v-infinite-scroll="load" class="blog-infinite-list" style="overflow: auto;" infinite-scroll-distance="20">
			<div v-for="i in count" :key="i" class="blog-infinite-list-item" native="true">
				<img class="blog-article-imag" src="../../assets/logo.png"/>
				<div class="blog-main">
					<div class="blog-article">
						<h2>标题</h2>
						<div>文章</div>
					</div>

					<div class="blog-detail">
						<div>作者</div>
						<img src="../../assets/imags/眼睛.png" style="height: 20px;"/>
						<div>阅读数量</div>	

						<img src="../../assets/imags/眼睛.png" style="height: 20px;"/>
						<div>点赞数量</div>

						<img src="../../assets/imags/眼睛.png" style="height: 20px;"/>
						<div>踩数量</div>

						<img src="../../assets/imags/眼睛.png" style="height: 20px;"/>
						<div>收藏数量</div>	
					
					</div>
				</div>
				
			</div>
		</div>	
	</div>
	
</template>


<script setup>
import { ref } from 'vue'
const count = ref(20)
let pageNumber = 1;
let pageSize = 10;
const load = () => {
	// if(count.value < 20)
  pageSize+=10;
}

</script>

<style src="../../assets/css/blog.css" scoped>

</style>