<template>
	<div id="app">
		<el-row>
			<el-col :xs="3" :sm="3" :md="3" :lg="2">&nbsp;</el-col>			
			<el-col :xs="18" :sm="18" :md="18" :lg="20">
				<TopMenu></TopMenu>
				<router-view></router-view>
			</el-col>
			<el-col :xs="3" :sm="3" :md="3" :lg="2">&nbsp;</el-col>
		</el-row>		
	</div>
</template>

<script>
	import TopMenu from "./components/TopMenu.vue"
	export default {
		name: 'app',
		components: {
			TopMenu
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