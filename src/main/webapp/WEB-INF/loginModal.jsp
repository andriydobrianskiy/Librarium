<%--
  Created by IntelliJ IDEA.
  User: Volodymyr
  Date: 5/27/2019
  Time: 1:50 PM
  To change this template use File | Settings | File Templates.
--%>
<!-- The Modal -->
<div class="modal fade" id="loginModal">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">Login</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <!-- Modal body -->
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/login" method="post" class="needs-validation" novalidate>
                    <div class="form-group">
                        <label for="uname">Username:</label>
                        <input type="text" class="form-control" id="uname" placeholder="Enter username" name="uname" required>
                        <div class="invalid-feedback">Please fill out this field.</div>
                    </div>
                    <div class="form-group">
                        <label for="pwd">Password:</label>
                        <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="pswd" required>
                        <div class="invalid-feedback">Please fill out this field.</div>
                    </div>

                    <button type="submit" class="btn btn-primary">Submit</button>
                    <button type="button" class="btn btn-danger" style="float:right" data-dismiss="modal">Close</button>
                </form>
            </div>


        </div>
    </div>
</div>
