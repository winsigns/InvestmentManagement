<template> 
    <div>
        <el-row>
            <div class="line_bottom"><h1>基本信息</h1> </div>    
            <div class="line_margin_top"></div>
        </el-row>
        <el-row>        
            <el-col :xs="16" :sm="14" :md="12" :lg="8" :offset="3">                
                <el-form v-loading="loading" :label-position="right" :rules="rules" ref="fundDetail" label-width="100px" :model="fundDetail">
                    <el-form-item label="基金代码" prop="code">
                        <el-input v-model="fundDetail.code"></el-input>
                    </el-form-item>
                    <el-form-item label="基金名称" prop="name">
                        <el-input v-model="fundDetail.name"></el-input>
                    </el-form-item>
                    <el-form-item label="基金简称" prop="shortName">
                        <el-input v-model="fundDetail.shortName"></el-input>
                    </el-form-item>
                    <el-form-item label="份额">
                        <el-input v-model="fundDetail.totalShares" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm('fundDetail')">保存</el-button>
                        <el-button @click="cancelForm()">取消</el-button>
                    </el-form-item>
                </el-form>   
            </el-col>	                     
        </el-row>             
    </div>
</template>
<script>
    import api from '../../config/api.json'
    import ds from '../../common/ds'
    export default{
        data(){
            return {
                loading:true,
                fundDetail:{
                    code: '',
                    name: '',
                    shortName: ''
                },
                rules:{
                    code: [
                        { required: true, message: '请输入基金代码', trigger: 'blur' }                    
                    ],
                    name: [
                        { required: true, message: '请输入基金名称', trigger: 'blur' }                    
                    ],
                    shortName: [
                        { required: true, message: '请输入基金简称', trigger: 'blur' }                    
                    ]            
                }
            }
        },
        mounted: function(){
           var _self = this;             
            ds.GET({url:api.fundURL.funds+_self.$route.params.fundId,
                    data:{}},function(data){ 
                _self.loading = false;  
                _self.fundDetail = data;             
            })   
        },
        methods: {
           submitForm(fundProp) {
                var _self = this;            
                _self.$refs[fundProp].validate((valid) => {
                    if (valid) {
                         ds.PUT({url:api.fundURL.funds+'/'+_self.$route.params.fundId,
                                data:{
                                    code:_self.fundDetail.code, 
                                    name:_self.fundDetail.name,
                                    shortName:_self.fundDetail.shortName}},
                        function(data){                                          
                            //创设成功
                            _self.$message({
                                message: '修改成功',
                                type: 'success'
                            });
                            _self.$router.push({ name: 'Fund', params: {}})
                        },function(data){
                            _self.$message.error('修改失败');
                        }) 
                    } else {                        
                        return false;
                    }
                });
            },cancelForm(){
                this.$router.push({ name: 'Fund', params: {}})
            }
        }
    }
</script>
<style scoped>
   
</style>