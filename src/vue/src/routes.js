import Login from './views/login.vue';
import NotFound from './views/404.vue';
import Home from './views/home.vue';
import Main from './views/main.vue';
import settings from './views/saleman/settings.vue';
import customers from './views/customer/list';
import appointments from './views/appointment/list';
let routes = [
    {
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    },

    {
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },
    {
        path: '/',
        component: Home,
        name: '',
        hidden: true,
        children: [
            { path: '/main', component: Main, name: '主页', hidden: true },
            { path: '/saleman/setting', component: settings, name: '账户设置' },
        ]
    },
    {
        path: '/',
        component: Home,
        name: '客户管理',
        iconCls: 'fa fa-user',//图标样式class
        children: [
            { path: '/main', component: Main, name: '主页', hidden: true },
            { path: '/customer/list', component: customers, name: '客户列表' },
            { path: '/appointment/list', component: appointments, name: '预约列表' }

        ]
    },
  /*  {
        path: '/',
        component: Home,
        name: 'Charts',
        iconCls: 'fa fa-bar-chart',
        children: [
            { path: '/echarts', component: echarts, name: 'echarts' }
        ]
    },*/
    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
];

export default routes;