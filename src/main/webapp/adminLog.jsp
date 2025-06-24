<%@ page import="model.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Quản lý Log</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="description">
    <meta content="" name="keywords">

    <script type="text/javascript" src="./assets/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="./assets/js/js_bootstrap4/bootstrap.min.js"></script>
    <script type="text/javascript" src="./assets/js/toast.js"></script>
    <script type="text/javascript" src="./assets/js/admin.js"></script>
    <script type="text/javascript" src="./assets/js/adminCustomer.js"></script>


    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

    <!-- Vendor CSS Files -->
    <link href="./assets/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="./assets/css/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="./assets/fonts/fonts-bootstrap/bootstrap-icons.min.css">
    <link href="./assets/css/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
    <link href="./assets/css/vendor/quill/quill.snow.css" rel="stylesheet">
    <link href="./assets/css/vendor/quill/quill.bubble.css" rel="stylesheet">
    <link href="./assets/css/vendor/remixicon/remixicon.css" rel="stylesheet">
    <link href="./assets/css/vendor/simple-datatables/style.css" rel="stylesheet">

    <!-- Template Main CSS File -->
    <link href="./assets/css/responsive.css" rel="stylesheet">
    <link href="./assets/css/style.css" rel="stylesheet">
    <link href="./assets/css/base.css" rel="stylesheet">
    <link href="./assets/css/modal.css" rel="stylesheet">
    <link href="./assets/css/toast.css" rel="stylesheet">



    <link href="./assets/css/adminBase.css" rel="stylesheet">
    <link href="./assets/css/adminLog.css" rel="stylesheet">


</head>
<body>

<%@ include file="adminHeader.jsp" %>
<div class="flex-roww">
    <%@ include file="adminMenu.jsp" %>
    <%
        ArrayList<Log> logs = (ArrayList<Log>) request.getAttribute("logs");
        int totalPage = request.getAttribute("totalPage")!=null?(int) request.getAttribute("totalPage"):0;
//        int cateID = request.getAttribute("cateID")!=null?(int) request.getAttribute("cateID"):Constant.ALL;

        String message = (String) request.getAttribute("message");
        String script = (String) request.getAttribute("script");
        if (script==null) script="";
    %>

    <%
        if(message!=null) {
    %>
    <script>
        alert('<%= script %>');
    </script>
    <%

        }
    %>

    <main id="main" class="main grid-col-10 adminMain_tab grid-col-12_tab adminMain_mob grid-col-12_mob adminCustomer_mob">
        <div id="toast"></div>
        <div id="toast-2"></div>
        <div class="pagetitle">
            <div class="flex-roww" style="align-items: start;">
                <button class="btn btn-outline-primary menu-btn hide show_tab" onclick="showMenu()"><i class="bi bi-list"></i></button>
                <div>
                    <h1>Quản lý Log</h1>
                    <nav>
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="index">Trang chủ</a></li>
                            <li class="breadcrumb-item active">Quản lý Log</li>
                        </ol>
                    </nav>
                </div>
            </div>

        </div><!-- End Page Title -->
        <div class="sub-content">
            <div class="flex-roww" style="justify-content: space-between">
                <%if(userLoggingMenu.hasRole("CUSTOMER")) {%>
                    <a class="btn btn-outline-primary <%=adminMenu.equalsIgnoreCase("customer")?"active":""%>" href="alog?a=query&area=customer&level=1"></a>
                <%
                    }
                %>
                <%if(userLoggingMenu.hasRole("EMPLOYEE")) {%>
                    <a class="btn btn-outline-primary <%=adminMenu.equalsIgnoreCase("employee")?"active":""%>" href="alog?a=query&area=employee&level=1"></a>
                <%
                    }
                %>
                <%if(userLoggingMenu.hasRole("PRODUCT")) {%>
                    <a class="btn btn-outline-primary <%=adminMenu.equalsIgnoreCase("product")?"active":""%>" href="alog?a=query&area=product&level=1"></a>
                <%
                    }
                %>
                <%if(userLoggingMenu.hasRole("ORDER")) {%>
                    <a class="btn btn-outline-primary <%=adminMenu.equalsIgnoreCase("order")?"active":""%>" href="alog?a=query&area=order&level=1"></a>
                <%
                    }
                %>
            </div>
            <div class="info-container">
                <table style="width: 100%;" id="infoTable" class="info-table">
                    <thead>
                    <tr>
                        <th scope="col" class="grid-col-0_5 text-center" style="height: fit-content;">ID</th>
                        <th scope="col" class="grid-col-0_5 text-center">Mức độ</th>
                        <th scope="col" class="grid-col-2">Địa chỉ IP </br> Thông tin </br> Thời gian</th>
                        <th scope="col" class="grid-col-2">Hành động</th>
                        <th scope="col" class="grid-col-1">Giá trị trước</th>
                        <th scope="col" class="grid-col-1">Giá trị sau</th>
                    </tr>
                    </thead>
                    <tbody class="group">
                    <%
                        int stt=1;
                        for(Log log : logs) {
                    %>
                            <tr class="group level-<%=log.getLevel()%>" id="log<%=log.getId()%>">
                                <th scope="row" class="grid-col-0_5 text-center" >
                                    <span class="stt"><%=log.getId()%></span>
                                </th>
                                <td class="grid-col-2">
                                    <%=log.getIp()%> </br> <%=log.getInfo()%> </br>  <%=log.getTime()%>
                                </td>
                                <td class="grid-col-2">
                                    <%=log.getAction()%>
<%--                                    <input class="info-input" type="email" name="email" value="<%=u.email%>" readonly>--%>
                                </td>
                                <td class="grid-col-1 text-center">
                                    <%=log.getPreValue()%>
                                </td>
                                <td class="grid-col-1 text-center">
                                    <%=log.getAfterValue()%>
                                </td>

                            </tr>
                    <%
                        }
                    %>


                    </tbody>
                </table>

                <div class="flex-roww" style="justify-content: center;margin-top: 20px">
                    <nav aria-label="Page navigation example" id="log-pagination">
                        <ul class="pagination group" style="align-items: baseline;">
                            <li class="page-item-nav">
                                <a class="page-link" href="#" aria-label="Previous" onclick="previousPage(event)">
                                    <span class="sr-only">Trước</span>
                                </a>
                            </li>

                            <% for (int i = 1; i <= totalPage; i++) {

                            %>
                            <li class="page-item">
                                <a class="page-link" data-value="<%=i%>" href="alog?a=query&page=<%=i%>"><%=i%></a>
                            </li>
                            <%=i == 1 ? "<div class=\"flex-roww\" style=\"align-items: baseline\"><i class=\"bi bi-three-dots etc\" style=\"margin: 0 10px;\"></i></div>\n" : ""%>
                            <%=i == (totalPage - 1) ? "<div class=\"flex-roww\" style=\"align-items: baseline\"><i class=\"bi bi-three-dots etc\" style=\"margin: 0 10px;\"></i></div>\n" : ""%>
                            <%
                                }
                            %>

                            <li class="page-item-nav">
                                <a class="page-link" href="#" aria-label="Next" onclick="nextPage(event)">
                                    <!--              <span aria-hidden="true">&raquo;</span>-->
                                    <span class="sr-only">Tiếp theo</span>
                                </a>
                            </li>
                        </ul>

                    </nav>
                </div>

                <script>
                    setUpPagination('#log-pagination');
                </script>

            </div>

        </div>

    <div id="alog-response"></div>
    </main><!-- End #main -->
</div>
<%=script%>

</body>
</html>