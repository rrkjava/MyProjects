<!DOCTYPE html>
<html data-ng-app="app" lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>JPS FDC</title>
    <link rel='icon' href='assets/images/jps-logo.png' type='image/x-icon'>


    <!-- endbuild -->
     <link href="assets/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="assets/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="assets/vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- iCheck -->
    <link href="assets/vendors/iCheck/skins/flat/green.css" rel="stylesheet">
     <!-- bootstrap-daterangepicker -->
    <link href="assets/vendors/daterangepicker/daterangepicker.css" rel="stylesheet">
    <!-- Custom Theme Style -->
    <link href="assets/css/custom.min.css" rel="stylesheet">
    <!-- Custom toggle  -->
    <link href="assets/css/toggle-button.css" rel="stylesheet">
    <!-- Custom switch  -->
    <link href="assets/css/switch-checkbox.css" rel="stylesheet">
    <style type="text/css">
      /** loader **/
      .loader {
        border: 5px solid #00bee9;
        border-top: 5px solid #005278;
        border-bottom: 5px solid #006c00;
        border-left: 5px solid #ccff84;
        border-radius: 50%;
        width: 50px;
        height: 50px;
        margin: 20% auto;
        animation: spin 2s linear infinite;
      }

      @keyframes spin {
        0% { transform: rotate(0deg); }
        100% { transform: rotate(360deg); }
      }
    </style>
   
</head>
<body ng-controller="AppController" class="nav-md">

<!--GET UAP PID FROM HEADER -->
  <%
    String pidValue=request.getHeader("REMOTE_USER");

    pidValue="sIa15CkgN3guFcVZiihdYbOL4diwAR"; // sample PID
    out.println(" UAP : "+ pidValue); // test : to be commented out
  %>
  <script>
    var pid = "<%=pidValue%>"; // setting uap pid to javascript variable to set it to angular scope
  </script>

    <div class="container body">
      <div class="main_container">
        <div class="col-md-3 left_col">
          <div class="left_col scroll-view">
            <div class="navbar nav_title" style="border: 0;">
              <a href="index.html" class="site_title"><img src="assets/images/jps-logo.png"> <span>JPS FDC</span></a>
            </div>

            <div class="clearfix"></div>

            <!-- menu profile quick info -->
            <div class="profile">
              <div class="profile_pic">
                <img src="assets/images/img.jpg" alt="..." class="img-circle profile_img">
              </div>
              <div class="profile_info">
                <h2>Admin</h2>
                <span>Admin Manager</span>
              </div>
            </div>
            <!-- /menu profile quick info -->

            <br />

            <!-- sidebar menu -->
            <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
              <div class="menu_section">
               <div class="menusep"></div>
                <ul class="nav side-menu">
                  <li><a href="#/home"><i class="fa fa-user"></i> Home </a></li>
                  <li><a href="#/users"><i class="fa fa-user"></i> User </a></li>
                  <li><a href="#/dissemination"><i class="fa fa-arrows-alt"></i> Dissemination </a></li>
                  <li><a><i class="fa fa-bar-chart"></i> Report / Statistic <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="#/statistics">Detail Report</a></li>
                      <li><a href="#/summary">Summary Report</a></li>
                    </ul>
                  </li>
                 <!--  <li><a href="#/settings"><i class="fa fa-gear"></i> Application Settings </a></li> -->
                  <li><a href="#/agency"><i class="fa fa-group"></i> Agency / Domain </a></li>
                </ul>
              </div>
  
            </div>
            <!-- /sidebar menu -->


          </div>
        </div>

        <!-- top navigation -->
        <div class="top_nav">
          <div class="nav_menu">
            <nav>
              <div class="nav toggle">
                <a id="menu_toggle"><i class="fa fa-bars"></i></a>
              </div>

              <ul class="nav navbar-nav navbar-right">
                <li class="">
                  <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                    <img src="assets/images/img.jpg" alt="">{{pid}} <!--PID TESTING -->
                      {{userDetails.givenName}}
                    <span class=" fa fa-angle-down"></span>
                  </a>
                  <ul class="dropdown-menu dropdown-usermenu pull-right">
                   <!--  <li><a href="javascript:;"> Profile</a></li>
                    <li>
                      <a href="javascript:;">
                        <span class="badge bg-red pull-right">50%</span>
                        <span>Settings</span>
                      </a>
                    </li> -->
                    <li><a href="https://11.1.32.24/uapsso/Logout"><i class="fa fa-sign-out pull-right"></i> Log Out</a></li>
                  </ul>
                </li>
              </ul>
            </nav>
          </div>
        </div>

    <section id="main">
        <div class="container">
            <ng-view></ng-view>
        </div>
    </section>
      <!-- footer content -->
        <footer>
          <div class="pull-right">
            © JPS FDC 2016
          </div>
          <div class="clearfix"></div>
        </footer>
        <!-- /footer content -->

    <!-- build:assets assets.min.js -->
    <!-- ASSETS -->
        <script src="assets/js/angular.min.js"></script>
        <script src="assets/js/angular-animate.min.js"></script>
        <script src="assets/js/angular-cookies.min.js"></script>
        <script src="assets/js/angular-route.min.js"></script>
        <script src="assets/js/angular-touch.min.js"></script>
        <script src="assets/js/moment.min.js"></script>
        <script src="assets/js/angular-moment.min.js"></script>
        <script src="assets/js/angular-truncate.js"></script>
        <script src="assets/js/angular-preload-image.min.js"></script>
    <!-- / -->
    <!-- endbuild -->
    <!-- build:js app.min.js -->
    <!-- MODULES -->
        <script src="app.routes.js"></script>
        <script src="app.config.js"></script>
        <script src="app.core.js"></script>
        <script src="app.services.js"></script>
        <script src="app.js"></script>
    <!-- / -->
    <!-- CONTROLLERS -->
        <script src="sections/home/home.ctrl.js"></script>
        <script src="sections/users/users.ctrl.js"></script>
        <script src="sections/dissemination/dissemination.ctrl.js"></script>
        <script src="sections/statistics/statistics.ctrl.js"></script>
        <script src="sections/settings/settings.ctrl.js"></script>
        <script src="sections/agency/agency.ctrl.js"></script>
        <script src="sections/summary/summary.ctrl.js"></script>
        <script src="sections/summary/summary.ctrl.js"></script>
    <!-- / -->
    <!-- SERVICES -->
        <script src="services/show.fct.js"></script>
        <script src="services/page.val.js"></script>
    <!-- / -->
    <!-- DIRECTIVES -->
        <script src="components/show/show.drct.js"></script>
        <script src="directives/ngEnter.drct.js"></script>
    <!-- / -->
    <!-- inject:js -->
    <!-- endinject -->
    <!-- endbuild -->
    <!-- From UX team-->
     <script src="assets/vendors/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="assets/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- FastClick -->
    <script src="assets/vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="assets/vendors/nprogress/nprogress.js"></script>
    <!-- iCheck -->
    <script src="assets/vendors/iCheck/icheck.min.js"></script>

    <!-- bootstrap-daterangepicker -->
    <script src="assets/vendors/moment/min/moment.min.js"></script>
    <script src="assets/vendors/daterangepicker/daterangepicker.js"></script>

    <!-- Custom Theme Scripts -->
    <script src="assets/js/ux/custom.min.js"></script>

    <!--Pagination -->
    <script src="assets/js/ux/dirPagination.js"></script>

</body>
</html>