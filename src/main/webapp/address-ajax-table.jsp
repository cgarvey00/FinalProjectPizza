<%@ page import="com.finalprojectcoffee.entities.Address" %><%
    Address address = (Address) request.getSession().getAttribute("addressDetails");
%>
<!-- styles css link  -->
<section class="products" style="padding-top: 0; min-height:100vh;">
    <div class="box-container">
        <h2 class="heading">Your Addresses</h2>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Street</th>
                <th>Town</th>
                <th>County</th>
                <th>EirCode</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><%=address.getStreet() %>
                </td>
                <td><%=address.getTown() %>
                </td>
                <td><%=address.getCounty() %>
                </td>
                <td><%=address.getEirCode() %>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</section>