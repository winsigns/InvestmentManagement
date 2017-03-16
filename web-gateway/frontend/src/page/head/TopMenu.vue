<template>
    <div>        
        <el-menu mode="horizontal" @select="handleSelect">         
          <el-submenu index="2">
            <template slot="title">基金产品</template>
            <el-menu-item index="2-1" @click="goRoute('FundCreate',{})">创设基金</el-menu-item>
            <el-menu-item index="2-2" @click="goRoute('Fund',{})">所有基金产品</el-menu-item>             
          </el-submenu>
          <set-lang></set-lang>          
        </el-select>                  
        </el-menu>        
    </div>
</template>

<script>    
import SetLang from "../../components/lang/SetLang.vue"
  export default {
    data() {
      return {
        
      }
    },
    components: {
      "set-lang":SetLang
    },
    mounted:function(){
       
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
<style>
  .lang-select{
    margin-top:10px;width:100px;float:right
  }
</style>