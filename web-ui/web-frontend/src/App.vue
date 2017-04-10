<template>
	<div id="app">
		<top-menu></top-menu>
		<el-row>			
			<el-col :offset="2" :span="20">
				<router-view></router-view>
			</el-col>			
		</el-row>	
		<main-footer></main-footer>
	</div>
</template>

<script>
	import TopMmenu from "./page/head/TopMenu.vue"
	import MainFooter from "./page/head/MainFooter.vue"
	export default {
		name: 'app',
		components: {
			"top-menu":TopMmenu,
			"main-footer": MainFooter
		}
	}
</script>

<style>	
	body {
		margin: 0px;
		padding: 0px;
		font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Microsoft YaHei, SimSun, sans-serif;
		font-weight: 400;
		-webkit-font-smoothing: antialiased;
	}
	
	#app {
		position: absolute;
		top: 0px;
		bottom: 0px;
		width: 100%;
		margin-top:0px; 
	}
	
</style>