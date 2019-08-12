项目名称：易购网上购物系统 

开发工具：Myeclipse、jkd1.8、Tomcat7、linux

技术实现：SpringMVC+Spring+Mybatis+Solr+Redis+Dubbo

项目需求：用户在线完成注册、登录后可以访问首页、搜索商品、加入购物车的等。在后台可以实现新增、编辑、下架商品，新增分类等功能。

功能模块：该项目基于ssm框架实现的，部署在linux服务器上完成本地测试，SOA架构分为

一．前台：门户项目、商品搜索、商品展示、购物车、注册登录、订单提交等

二．后台：商品管理项目、CMS等

SSO：用redis代替session实现不同tomcat服务器之间用户信息的传递，用httpclient发送cookie获取登录信息

商品搜索：用solr服务器实现商品的搜索功能，上传图片和回显图片用vsptd服务和nginx服务，而且用nginx实现负载均衡。

购物车-订单功能：该功能需要登录验证后才能访问（添加拦截器，用户添加的商品放入redis中，当完成订单后，修改、删除redis中购物车的信息
