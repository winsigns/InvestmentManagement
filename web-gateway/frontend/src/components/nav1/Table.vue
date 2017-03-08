<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-form :inline="true" :model="formInline" class="demo-form-inline">
				<el-form-item>
					<el-input v-model="formInline.user" placeholder="姓名"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button @click="query">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button @click="handleAdd">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<template>
			<el-table :data="tableData" stripe highlight-current-row v-loading="listLoading" 
            element-loading-text="拼命加载中" style="width: 100%;">
				<el-table-column type="index" width="80">
				</el-table-column>
				<el-table-column prop="userName" label="姓名" width="120" sortable>
				</el-table-column >
				<!-- <el-table-column prop="sex" label="登录名" width="100" :formatter="formatSex" sortable>
				</el-table-column> -->
                <el-table-column prop="userLogin" label="登录名"   sortable>
                </el-table-column>
				<el-table-column prop="userType" label="用户类型"  sortable>
				</el-table-column>
				<el-table-column prop="status" label="状态"  width="120" :formatter="formatStatus" sortable>
				</el-table-column>
				<el-table-column prop="userId" label="用户ID" sortable>
				</el-table-column>
				<el-table-column inline-template :context="_self" label="操作" width="100">
					<span>
					<el-button type="text" size="small" 
                        @click="openEditItemDialog(row)">编辑</el-button>
					<el-button type="text" size="small" @click="handleDel(row)">删除</el-button>
				</span>
				</el-table-column>
			</el-table>
		</template>

        <!--分页-->
        <el-col :span="24" class="toolbar" style="padding-bottom:10px;">
            <el-pagination :current-page="1" :page-sizes="[10, 20, 30, 40]" :page-size="10" layout="total, sizes, prev, pager, next, jumper" :total="57" style="float:right">
            </el-pagination>
        </el-col>
        <el-dialog :title="editFormTtile" v-model="editFormVisible" :close-on-click-modal="false">
            <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="editForm.name" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <!--<el-select v-model="editForm.sex" placeholder="请选择性别">
        						<el-option label="男" :value="1"></el-option>
        						<el-option label="女" :value="0"></el-option>
        					</el-select>-->
                    <el-radio-group v-model="editForm.sex">
                        <el-radio class="radio" :label="1">男</el-radio>
                        <el-radio class="radio" :label="0">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="年龄">
                    <el-input-number v-model="editForm.age" :min="0" :max="200"></el-input-number>
                </el-form-item>
                <el-form-item label="生日">
                    <el-date-picker type="date" placeholder="选择日期" v-model="editForm.birth"></el-date-picker>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input type="textarea" v-model="editForm.addr"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="editFormVisible = false">取 消</el-button>
                <el-button type="primary" @click.native="editSubmit" :loading="editLoading">{{btnEditText}}</el-button>
            </div>
        </el-dialog>
        <modal-dialog :mode="mode" :title="title" :item="item" :fields="columns" >
        </modal-dialog>
    </section>
</template>

<script>
    import util from '../../common/util'
    import api from '../../common/api'
    import ds from '../../common/ds'
    import modalDialog from "../dialog/ModalDialog"
    import bus from '../eventbus/bus'
    import NProgress from 'nprogress'

    export default {
        components: {
          'modal-dialog': modalDialog
        },
        data() {
            return {
                formInline: {
                    user: ''
                },
                pickerOptions0: {
                    disabledDate(time) {
                        return time.getTime() < Date.now() - 8.64e7;
                    }
                },
                value1: '',
                editFormVisible: false, //编辑界面显是否显示
                editFormTtile: '编辑', //编辑界面标题
                //编辑界面数据
                editForm: {
                    id: 0,
                    name: '',
                    sex: -1,
                    age: 0,
                    birth: '',
                    addr: ''
                },
                editLoading: false,
                btnEditText: '提 交',
                editFormRules: {
                    name: [{
                        required: true,
                        message: '请输入姓名',
                        trigger: 'blur'
                    }]
                 },
                tableData:[],            
                listLoading: false,
                //对话框
                mode: 0,
                title: '',
                keyColumn: '',
                item: {}

            }
        },
        created: function () {
            var _self = this;  
            ds.GET({url:api.systemURL.login,
                        data:{"username": "wfl@163.com",
                        "password": "wfl123"}},function(data){
                            ds.GET({  
                                url:api.systemURL.userSearch,
                                data: {},
                            },function(userdata){
                                _self.tableData = userdata.data;
                            })
                })       
          },
        methods: {
             openEditItemDialog: function (row) {
                //对话框的标题
                this.title = '编辑 - ' + row.userName 
                // mode = 2表示修改模式
                this.mode = 2
                // 初始化this.item
                this.item = this.initItemForUpdate(row)
                // 广播事件，showDialog是modal-dialog组件的一个方法，传入参数true表示显示对话框
                bus.$emit('showDialog', true)
            },
            // 弹出修改数据的对话框时，使用对象的深拷贝
            initItemForUpdate(p, c) {
                c = c || {};
                for (var i in p) {
                    // 属性i是否为p对象的自有属性
                    if (p.hasOwnProperty(i)) {
                        // 属性i是否为复杂类型
                        if (typeof p[i] === 'object') {
                            // 如果p[i]是数组，则创建一个新数组
                            // 如果p[i]是普通对象，则创建一个新对象
                            c[i] = Array.isArray(p[i]) ? [] : {};
                            // 递归拷贝复杂类型的属性
                            this.initItemForUpdate(p[i], c[i]);
                        } else {
                            // 属性是基础类型时，直接拷贝
                            c[i] = p[i];
                        }
                    }
                }            
                return c;
            },
            //状态显示转换
            formatStatus: function(row, column) {
                var rStatus = '未知';
                if (row.status=='1'){
                    return '正常'
                } else if (row.status=='2'){
                    return '退出'
                } else if (row.status=='3'){
                    return '登录'
                } else if (row.status=='4'){
                    return '异常'
                } else {
                    return '未知'
                }
            },
            //删除记录
            handleDel: function(row) {
                //console.log(row);
                var _this = this;
                this.$confirm('确认删除该记录吗?', '提示', {
                    //type: 'warning'
                }).then(() => {
                    _this.listLoading = true;
                    NProgress.start();
                    setTimeout(function() {
                        for (var i = 0; i < _this.tableData.length; i++) {
                            if (_this.tableData[i].id == row.id) {
                                _this.tableData.splice(i, 1);

                                _this.listLoading = false;
                                NProgress.done();
                                _this.$notify({
                                    title: '成功',
                                    message: '删除成功',
                                    type: 'success'
                                });

                                break;
                            }
                        }
                    }, 5000);
                }).catch(() => {

                });
            },
            //显示编辑界面
            handleEdit: function(row) {
                this.editFormVisible = true;
                this.editFormTtile = '编辑';
                this.editForm.id = row.id;
                this.editForm.name = row.name;
                this.editForm.sex = row.sex;
                this.editForm.age = row.age;
                this.editForm.birth = row.birth;
                this.editForm.addr = row.addr;             
            },
            //编辑 or 新增
            editSubmit: function() {
                var _this = this;

                _this.$refs.editForm.validate((valid) => {
                    if (valid) {

                        _this.$confirm('确认提交吗？', '提示', {}).then(() => {
                            _this.editLoading = true;
                            NProgress.start();
                            _this.btnEditText = '提交中';
                            setTimeout(function() {
                                _this.editLoading = false;
                                NProgress.done();
                                _this.btnEditText = '提 交';
                                _this.$notify({
                                    title: '成功',
                                    message: '提交成功',
                                    type: 'success'
                                });
                                _this.editFormVisible = false;

                                if (_this.editForm.id == 0) {
                                    //新增
                                    _this.tableData.push({
                                        id: new Date().getTime(),
                                        name: _this.editForm.name,
                                        sex: _this.editForm.sex,
                                        age: _this.editForm.age,
                                        birth: _this.editForm.birth == '' ? '' : util.formatDate.format(new Date(_this.editForm.birth), 'yyyy-MM-dd'),
                                        addr: _this.editForm.addr
                                    });
                                } else {
                                    //编辑
                                    for (var i = 0; i < _this.tableData.length; i++) {
                                        if (_this.tableData[i].id == _this.editForm.id) {
                                            _this.tableData[i].name = _this.editForm.name;
                                            _this.tableData[i].sex = _this.editForm.sex;
                                            _this.tableData[i].age = _this.editForm.age;
                                            _this.tableData[i].birth = _this.editForm.birth == '' ? '' : util.formatDate.format(new Date(_this.editForm.birth), 'yyyy-MM-dd');
                                            _this.tableData[i].addr = _this.editForm.addr;
                                            break;
                                        }
                                    }
                                }
                            }, 1000);

                        });

                    }
                });
            },
            //显示新增界面
            handleAdd: function() {
                var _this = this;

                this.editFormVisible = true;
                this.editFormTtile = '新增';

                this.editForm.id = 0;
                this.editForm.name = '';
                this.editForm.sex = 1;
                this.editForm.age = 0;
                this.editForm.birth = '';
                this.editForm.addr = '';
            },
            //查询
            query: function(){
                console.log('dsd')
                ds.GET({url:'http://api.opensupporter.org/api/v1/people',
                        data:{}},function(data){
                            console.log('成功');
                        
                })      
               /* ds.GET({url:'http://api.opensupporter.org/api/v1/people/',
                        data:{}},function(data){
                            console.log('成功');
                            ds.GET({
                                url:'http://api.opensupporter.org/api/v1/questions',
                                data: {},
                            },function(userdata){
                                console.log('用户：')
                                console.log(userdata);
                            })
                })*/
            
            }
        }
    }
</script>

<style scoped>
    .toolbar .el-form-item {
        margin-bottom: 10px;
    }
    
    .toolbar {
        background: #fff;
        padding: 10px 10px 0px 10px;
    }
</style>