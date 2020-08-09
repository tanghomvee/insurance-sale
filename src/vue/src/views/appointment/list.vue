<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;" >
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.ownerName" placeholder="姓名"></el-input>
				</el-form-item>
				<el-form-item>
					<el-input v-model="filters.carNo" placeholder="车牌"></el-input>
				</el-form-item>

				<!--<el-form-item>
					<el-input v-model="filters.phoneNum" placeholder="手机号码"></el-input>
				</el-form-item>-->

				<el-form-item>
					<el-button type="primary" v-on:click="getContents">查询</el-button>
				</el-form-item>

				<el-form-item>
					<div class="tag-group">
						<el-tag
								v-for="tag in tags"
								:key="tag.txt"
								:type="tag.type"
								effect="dark"
								style="margin-left: 5px;height: 35px;line-height: 35px;"
								@click.native="search(tag)">
							{{tag.txt}}
						</el-tag>
					</div>
				</el-form-item>

			</el-form>


		</el-col>

		<!--列表-->
		<el-table :data="contents" highlight-current-row v-loading="listLoading"  style="width: 100%;">
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column type="index" width="60">
			</el-table-column>
			<el-table-column prop="carNo" label="车牌" width="auto">
			</el-table-column>
			<el-table-column prop="frameNo" label="车架" width="auto">
			</el-table-column>
			<el-table-column prop="ownerName" label="姓名" width="auto">
			</el-table-column>

			<el-table-column prop="stateTxt" label="预约状态" width="auto">
			</el-table-column>


			<el-table-column prop="startDate" label="保险公司" width="auto" :formatter="formatTime">
			</el-table-column>
			<el-table-column prop="endDate" label="到期日期" width="auto" :formatter="formatTime">
			</el-table-column>
			<el-table-column prop="note" label="预约备注" width="auto">
			</el-table-column>

			<el-table-column label="操作" width="200">
				<template scope="scope">
					<el-button size="small" type="success" @click="showAppointmentForm(scope.$index, scope.row)">查看详情</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="20" :total="total" style="float:right;">
			</el-pagination>
		</el-col>
		

		<!--详情信息-->
		<el-dialog title="详情信息" v-model="detailFormVisible" :close-on-click-modal="false">
			<el-form :model="detailForm" label-width="80px"  ref="detailForm">
				<el-form-item label="车牌">
					<el-input type="input" v-model="detailForm.carNo" :disabled="true"></el-input>
				</el-form-item>
				<el-form-item label="车架">
					<el-input type="input" v-model="detailForm.frameNo" :disabled="true"></el-input>
				</el-form-item>
				<el-form-item label="姓名">
					<el-input type="input" v-model="detailForm.ownerName" :disabled="true"></el-input>
				</el-form-item>
				<el-form-item label="发牌日期">
					<el-input type="input" v-model="detailForm.licenseDate" :disabled="true"></el-input>
				</el-form-item>
				<el-form-item label="保险公司">
					<el-input type="input" v-model="detailForm.company" :disabled="true"></el-input>
				</el-form-item>
				<el-form-item label="到期日期">
					<el-input type="input" v-model="detailForm.finalInsDate" :disabled="true"></el-input>
				</el-form-item>
				<!--<el-form-item label="地址" >
					<el-input type="input" v-model="detailForm.addr" :disabled="true"></el-input>
				</el-form-item>
				<el-form-item label="短信记录" >
					<el-input type="textarea" :rows="8" v-model="detailForm.smsMsg" :disabled="true"></el-input>
				</el-form-item>-->
			</el-form>
			<!--预约记录-->
			<el-form :model="appointmentForm" label-width="80px" :rules="appointmentFormRules" ref="appointmentForm">

				<el-form-item label="预约结果" prop="state">
					<el-select v-model="appointmentForm.state" placeholder="请选择">
						<el-option
								v-for="item in states"
								:key="item.value"
								:label="item.label"
								:value="item.value">
						</el-option>
					</el-select>
				</el-form-item>

				<el-form-item label="预约时间" prop="appointmentDate">
					<!--<el-date-picker
							v-model="appointmentForm.appointmentDate"
							type="datetimerange"
							range-separator="至"
							start-placeholder="开始日期"
							end-placeholder="结束日期">
					</el-date-picker>-->
					<el-date-picker
							v-model="appointmentForm.appointmentDate"
							type="datetime"
							placeholder="选择日期时间">
					</el-date-picker>
				</el-form-item>

				<el-form-item label="预约备注" prop="note">
					<el-input type="textarea" :rows="2" v-model="appointmentForm.note"></el-input>
				</el-form-item>
			</el-form>

			<div slot="footer" class="dialog-footer">
				<el-button @click.native="detailFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="addAppointment" :loading="addLoading">提交</el-button>
			</div>
		</el-dialog>
	</section>
</template>

<script>
    import util from '../../common/js/util';
    import moment from 'moment'
    import NProgress from 'nprogress';
	import {
		editAppointment,
		oneCustomer,
		countAppointment,
		expiredAppointment,
			getPriceMsg,
		listAppointment
	} from '../../api/api';

    export default {
		data:function() {
			return {
				filters: {
					carNo:null,
					ownerName:null,
                    phoneNum: null,
					appointmentDate:null,
					state:null
				},
				tags:[{type:"danger",txt:"今天到期: 0 条"},
					{type:"warning",txt:"明天到期: 0 条"},
					{type:"success",txt:"后天到期: 0 条"},
					{type:"info",txt:"已经到期: 0 条"}],
				states:[
					{value:"SUCCESS",label:"预约成功"},
					{value:"FAIL_BUSY",label:"客户繁忙"},
					{value:"FAIL_NOT_CONNECTION",label:"联系不上"},
					{value:"FAIL_PURCHASED",label:"已购买"},
					{value:"FAIL_NOT_RECOGNIZED",label:"不认可"},
					{value:"FINISHED",label:"完成出单"},
					{value:"EXPIRED",label:"已过期"},
				],
                contents: [],
				total: 0,
				page: 1,
				listLoading: false,
				sels: [],//列表选中列


				detailFormVisible: false,//新增界面是否显示
				addLoading: false,
				//新增界面数据
				detailForm: {
					carId:"",
					carNo:"",
					frameNo:"",
					ownerName:"",
					licenseDate:"",
					company:"",
					finalInsDate:"",
					addr:"",
					smsMsg:""
				},

				appointmentFormRules: {
					appointmentDate: [
						{ required: true, message: '请选择预约日期' }
					],
					state: [
						{ required: true, message: '请选择预约状态', trigger: 'blur' }
					],
					note: [
						{ required: true, message: '请输入预约备注信息', trigger: 'blur' }
					]
				},
				appointmentForm:{
					appointmentDate:null,
					carNo:"",
					frameNo:"",
					ownerName:"",
					carId:"",
					state:"",
					startDate:"",
					endDate:"",
					note:""
				}


			}
		},
		methods: {
			//使用显示转换
            formatTime: function (row, column) {
				return row.createTime  ?  moment(row.createTime).format("YYYY-MM-DD HH:mm:ss") : "";
			},
			handleCurrentChange:function(val) {
				this.page = val;
				this.getContents();
			},
			//获取内容列表
			getContents:function() {
				let params = {
					pageNum: this.page - 1,
                    pageSize:10,
					carNo: this.filters.carNo,
					phoneNum: this.filters.phoneNum,
					ownerName: this.filters.ownerName,
					appointmentDate:this.filters.appointmentDate,
					state:this.filters.state,

				};
				this.listLoading = true;
				NProgress.start();
				listAppointment("/"+params.pageSize + "/"+params.pageNum , params , this).then((res) => {
                    if(!res){
                        return;
					}
					this.total = res.total;
					this.contents = res.data.data;
					this.listLoading = false;
					NProgress.done();
				});
			},

			//显示新增界面
			showAppointmentForm: function (index , row) {
				let _this = this;
				_this.appointmentForm = Object.assign({}, row);
				//_this.appointmentForm.appointmentDate= [_this.appointmentForm.startDate,_this.appointmentForm.endDate];
				oneCustomer({id:row.carId} , _this).then(function (res) {
					if(!res){
						return;
					}
					_this.detailFormVisible = true;
					_this.detailForm = Object.assign({}, res.data) ;
				});

				getPriceMsg({"frameNo":row.frameNo} , _this).then((res)=>{
					if(!res){
						return;
					}
					_this.$set(_this.detailForm,"smsMsg",res.data.content);

				})
			},
			//新增
			addAppointment: function () {
                let _this = this;
				this.$refs.appointmentForm.validate((valid) => {
					if (valid) {
						_this.appointmentForm.startDate = _this.appointmentForm.appointmentDate[0];
						_this.appointmentForm.endDate = _this.appointmentForm.appointmentDate[1];
						// _this.appointmentForm.carId = _this.detailForm.id;
						// _this.appointmentForm.carNo = _this.detailForm.carNo;
						// _this.appointmentForm.frameNo = _this.detailForm.frameNo;

						util.Msg.confirm(_this , null ,function () {
                            _this.addLoading = true;
                            NProgress.start();
                            let para = Object.assign({}, _this.appointmentForm);

							editAppointment(para,_this).then(function() {
                                _this.addLoading = false;
                                NProgress.done();
                                util.Msg.success(_this);
                                _this.$refs['appointmentForm'].resetFields();
                                _this.detailFormVisible = false;
                                _this.getContents();
                            });
                        });

					}
				});
			},
			count:function () {
            	let _this = this;
            	countAppointment({delta:0} , _this).then(function (res) {
					if(!res){
						return;
					}
					_this.$set(_this.tags,0,{type:"danger",txt:"今天到期:"+ res.data+" 条",appointmentDate:new Date()});
				});
            	countAppointment({delta:1} , _this).then(function (res) {
					if(!res){
						return;
					}
					let appointmentDate = new Date();
					appointmentDate.setDate(appointmentDate.getDate() + 1);
					_this.$set(_this.tags,1,{type:"warning",txt:"明天到期:"+ res.data+" 条",appointmentDate:appointmentDate});
				});
            	countAppointment({delta:2} , _this).then(function (res) {
					if(!res){
						return;
					}
					let appointmentDate = new Date();
					appointmentDate.setDate(appointmentDate.getDate() + 2);
					_this.$set(_this.tags,2,{type:"success",txt:"后天到期:"+ res.data+" 条",appointmentDate:appointmentDate});
				});
            	expiredAppointment({} , _this).then(function (res) {
					if(!res){
						return;
					}
					_this.$set(_this.tags,3,{type:"info",txt:"已经过期:"+ res.data+" 条",state:"EXPIRED"});
				});
			},
			search:function (tag) {
            	let _this = this;
            	console.info(tag);
            	_this.filters.appointmentDate = tag.appointmentDate;
            	_this.filters.state = tag.state;
            	_this.getContents();
			}
		},
		mounted() {
			setInterval(this.count,10000);
			this.getContents();
		}
	}

</script>

<style scoped>

</style>