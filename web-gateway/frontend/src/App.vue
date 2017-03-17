<template>
	<div id="app">
		<el-row>
			<top-menu></top-menu>
			<router-view></router-view>
		</el-row>		
	</div>
</template>

<script>
	import TopMmenu from "./page/head/TopMenu.vue"
	export default {
		name: 'app',
		components: {
			"top-menu":TopMmenu
		},
		created: function () {
			this.$router.replace('/Fund')
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