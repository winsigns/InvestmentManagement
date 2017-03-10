<template>
    <div>        
        <el-menu mode="horizontal" @select="handleSelect">         
          <el-submenu index="2">
            <template slot="title">基金产品</template>
            <el-menu-item index="2-1" @click="goRoute('FundCreate',{})">创设基金</el-menu-item>
            <el-menu-item index="2-2" @click="goRoute('Fund',{})">所有基金产品</el-menu-item>            
          </el-submenu>          
        </el-menu>
    </div>
</template>

<script>
  export default {
    data() {
      return {
        
      };
    },
    methods: {
      handleSelect(key, keyPath) {
        //console.log(key, keyPath);
      },
    goRoute: function(routeName, routeParams){
        this.$router.push({ name: routeName, params: routeParams})
    }
    }
  }
</script>