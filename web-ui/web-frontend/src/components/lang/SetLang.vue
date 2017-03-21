<template>
    <div>
        <el-select :change="setLang()" v-model="currentlang" >
            <el-option
                v-for="item in languages"
                :label="item.label"
                :value="item.value">
            </el-option>
        </el-select>
    </div>
</template>
<script>
    import $ from 'jquery2'
    import langcfg from "../../config/lang/config.json"
    import wslang from "../../common/lang.js"
    export default{
        data(){
            return {
                languages: langcfg,
                currentlang: wslang.readLanguage()
            }           
        },
        mounted:function(){
                     
        },
        methods:{
            setLang: function(callback){           
                wslang.setLanguage(this.currentlang);
                this.$i18n.locale = this.currentlang               
            }
        }
    }
</script>
<style>

</style>