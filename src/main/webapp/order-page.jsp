<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order</title>

    <!-- font awesome cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">

    <!-- bootstrap cdn link  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.6.1/css/bootstrap.min.css">
    <!-- styles css link  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles2.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/styles.css">
    <%--Google Map--%>
    <script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
    <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA6O152ByRjg91-NHc0XIfWhkps0sMG9V0&language=en&callback=initMap"></script>

</head>
<body>
<c:choose>
    <c:when test="${sessionScope.userType == 'customer'}">
        <jsp:include page="customer-nav.jsp"/>
    </c:when>
    <c:when test="${sessionScope.userType == 'admin'}">
        <jsp:include page="admin-nav.jsp"/>
    </c:when>
    <c:when test="${sessionScope.userType == 'employee'}">
        <jsp:include page="employee-nav.jsp"/>
    </c:when>
</c:choose>
<br><br><br><br><br><br><br><br><br><br>
<h1 class="heading">Order Page</h1>
<c:if test="${not empty sessionScope.orderItemsInOrder}">
    <div class="show-products">
        <div class="box-container">
            <div class="box bg-light">
                <div class="content text-dark">
                    <c:forEach var="orderItem" items="${sessionScope.orderItemsInOrder}">
                        <h3 class="text-dark"><c:out value="${orderItem.getProduct().getName()}"/></h3>
                        <img src="<c:url value='/images/${orderItem.getProduct().getImage()}' />" alt="image"
                             class="img-size">
                        <span style="font-size: 20px;"> x ${orderItem.getQuantity()} = ${orderItem.getCost()} &euro;</span>
                    </c:forEach>
                    <c:if test="${not empty sessionScope.addressInOrder}">
                        <h1 class="text-dark">Address</h1>
                        <h3>Street: <c:out value="${sessionScope.addressInOrder.getStreet()}"/></h3>
                        <h3>Eir Code: <c:out value="${sessionScope.addressInOrder.getEirCode()}"/></h3>
                    </c:if>
                    <c:if test="${not empty sessionScope.order}">
                        <h1>Balance: <c:out value="${sessionScope.order.getBalance()}"/> &euro;</h1>
                    </c:if>
                    <c:choose>
                        <c:when test="${sessionScope.order.pending && sessionScope.userType == 'customer'}">
                            <form action="controller" method="post">
                                <div class="button-container">
                                    <button type="submit" name="action" value="cancel-order" class="btn cancel-btn">
                                        Cancel
                                    </button>
                                    <button type="submit" name="action" value="to-payment" class="btn toPay-btn">To
                                        Pay
                                    </button>
                                    <input type="hidden" name="orderId" value="${sessionScope.order.getId()}">
                                    <input type="hidden" name="userType" value="customer">
                                </div>
                            </form>
                        </c:when>
                        <c:when test="${!sessionScope.order.cancelled && !sessionScope.order.finished && sessionScope.userType == 'admin'}">
                            <form action="controller" method="post">
                                <div class="button-container">
                                    <button type="submit" name="action" value="cancel-order" class="btn cancel-btn">
                                        Cancel
                                    </button>
                                    <button type="submit" name="action" value="finish-order" class="btn finish-btn">
                                        Finish
                                    </button>
                                    <input type="hidden" name="orderId" value="${sessionScope.order.getId()}">
                                    <input type="hidden" name="userType" value="admin">
                                </div>
                            </form>
                        </c:when>
                        <c:when test="${sessionScope.order.delivering && !sessionScope.order.cancelled && !sessionScope.order.finished && sessionScope.userType == 'employee'}">
                            <form action="controller" method="post">
                                <div class="button-container single-button">
                                    <button type="submit" name="action" value="finish-order" class="btn finish-btn">
                                        Finish
                                    </button>
                                    <input type="hidden" name="orderId" value="${sessionScope.order.getId()}">
                                    <input type="hidden" name="userType" value="employee">
                                </div>
                            </form>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    <c:if test="${sessionScope.order.delivering && sessionScope.userType == 'customer'}">
        <div class="map-container">
            <h2>Your order is on its way and will be with you around...</h2>
            <h2 id="estimatedTime"></h2>
            <!--The div element for the map -->
            <div id="map"></div>
            <!-- prettier-ignore -->
            <script async defer>(g=>{var h,a,k,p="The Google Maps JavaScript API",c="google",l="importLibrary",q="__ib__",m=document,b=window;b=b[c]||(b[c]={});var d=b.maps||(b.maps={}),r=new Set,e=new URLSearchParams,u=()=>h||(h=new Promise(async(f,n)=>{await (a=m.createElement("script"));e.set("libraries",[...r]+"");for(k in g)e.set(k.replace(/[A-Z]/g,t=>"_"+t[0].toLowerCase()),g[k]);e.set("callback",c+".maps."+q);a.src=`https://maps.${c}apis.com/maps/api/js?`+e;d[q]=f;a.onerror=()=>h=n(Error(p+" could not load."));a.nonce=m.querySelector("script[nonce]")?.nonce||"";m.head.append(a)}));d[l]?console.warn(p+" only loads once. Ignoring:",g):d[l]=(f,...n)=>r.add(f)&&u().then(()=>d[l](f,...n))})
            ({key: "AIzaSyA6O152ByRjg91-NHc0XIfWhkps0sMG9V0", v: "beta"});
            </script>
        </div>
    </c:if>
</c:if>

<jsp:include page="footer.jsp"/>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<script src="${pageContext.request.contextPath}/scripts/menu.js" type="text/javascript"></script>

<script>
    <%--  Google Map  --%>
    let map;
    let eirCode = "${sessionScope.order.getAddress().getEirCode()}";

    async function initMap() {
        const position = { lat: 53.98429, lng: -6.39337 };
        const shopIcon = "https://maps.google.com/mapfiles/kml/pal2/icon32.png";

        const { Map } = await google.maps.importLibrary("maps");

        map = new Map(document.getElementById("map"), {
            zoom: 13,
            center: position,
            mapId: "PIZZA_SHOP_ID",
        });

        const shopMarker = new google.maps.Marker({
            map: map,
            icon: shopIcon,
            position: position,
            title: "DKIT_Pizza_Shop",
        });

        const infoWindow = new google.maps.InfoWindow({
            content: "<h2>We are here!</h2>"
        });

        infoWindow.open(map, shopMarker);

        geocodeAndAddMarker(eirCode);
        calculateRoute(position, eirCode);
    }

    function geocodeAndAddMarker(eirCode){
        const geocoder = new google.maps.Geocoder();
        geocoder.geocode({'address': eirCode}, function (results, status){
            if(status ==='OK'){
                map.setCenter(results[0].geometry.location);
                new google.maps.Marker({
                    map: map,
                    position: results[0].geometry.location,
                    title: "Order_Destination"
                });
            } else {
                console.error("Geocode was not successful for the following reason: " + status);
            }
        });
    }

    function calculateRoute(from, to){
        const directionsService = new google.maps.DirectionsService();
        const directionsRenderer = new google.maps.DirectionsRenderer();
        directionsRenderer.setMap(map);

        directionsService.route({
            origin: from,
            destination: to,
            travelMode: 'DRIVING'
        }, function (response, status){
            if(status === 'OK'){
                directionsRenderer.setDirections(response);
                const route = response.routes[0];
                document.getElementById('estimatedTime').textContent = route.legs[0].duration.text;
            } else {
                console.error('Direction request failed to ' + status);
                document.getElementById('estimatedTime').textContent = "Failed to get duration: " + status;
            }
        });
    }

    initMap();
</script>

</body>
<style>
    .img-size {
        width: 100px !important;
        height: auto !important;
    }

    .button-container {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 20px;
        margin-bottom: 20px;
    }

    .button-container.single-button {
        justify-content: center;
    }

    .btn {
        color: white;
        padding: 10px 20px;
        font-size: 18px;
        border: none;
        cursor: pointer;
        border-radius: 5px;
        box-sizing: border-box;
        width: 44%;
        margin-inside: 6%;
    }

    .cancel-btn {
        background-color: #ff4d4d;
    }

    .cancel-btn:hover {
        background-color: #cc0000;
    }

    .toPay-btn, .finish-btn {
        background-color: #109acb;
    }

    .toPay-btn:hover, .finish-btn:hover {
        background-color: #017fbd;
    }

    #map {
        height: 500px;
        width: 500px;
        margin-top: 20px;
    }

    .map-container {
        margin-top: 30px;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }
</style>
</html>