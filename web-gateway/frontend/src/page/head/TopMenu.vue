<template>
    <div>                              
      <el-row>      
        <el-col :xs="1" :sm="1" :md="1" :lg="1">&nbsp;</el-col>
        <el-col :xs="4" :sm="4" :md="4" :lg="4"><img src="../../assets/images/webwxgetmsgimg.png" class="logo"></el-col>			
        <el-col :xs="1" :sm="1" :md="1" :lg="1">&nbsp;</el-col>
        <el-col :xs="17" :sm="17" :md="17" :lg="17">
          <el-menu mode="horizontal" @select="handleSelect" class="top-munu">                 
            <template  v-for="menu in menuList">        
              <el-submenu :index="menu.index">
                <template slot="title">{{menu.caption}}</template>
                <el-menu-item v-for="submenu in menu.children" :index="submenu.index"
                @click="goRoute(submenu.route,submenu.param)">{{submenu.caption}}</el-menu-item>            
              </el-submenu>
            </template>               
            <set-lang class="lang-select"></set-lang>                         
          </el-menu>  
        </el-col>
        <el-col :xs="1" :sm="1" :md="1" :lg="1">&nbsp;</el-col>
      </el-row>	      
    </div>
</template>

<script>    
import SetLang from "../../components/lang/SetLang.vue"
import menuCfg from "../../config/menu.json"
  export default {
    data() {
      return {
          menuList: []
      }
    },
    components: {
      "set-lang":SetLang
    },
    mounted:function(){
       this.menuList = menuCfg;
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
<style scoped>
  .lang-select{
    margin-top:10px;width:100px;float:right
  }
  .top-munu{
    background:white;
  }
  .logo{
    float:left;
    position:relative; 
    z-index:99999
  }
</style>