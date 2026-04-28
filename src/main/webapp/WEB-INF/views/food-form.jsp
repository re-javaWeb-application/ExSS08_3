<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thêm món ăn</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f6f8fa;
            color: #1f2937;
        }

        main {
            width: min(760px, calc(100% - 32px));
            margin: 40px auto;
            background: #ffffff;
            border: 1px solid #e5e7eb;
            border-radius: 8px;
            padding: 28px;
        }

        h1 {
            margin: 0 0 24px;
            font-size: 28px;
        }

        form {
            display: grid;
            gap: 16px;
        }

        label {
            display: grid;
            gap: 8px;
            font-weight: 600;
        }

        input,
        select,
        textarea {
            width: 100%;
            box-sizing: border-box;
            border: 1px solid #cbd5e1;
            border-radius: 6px;
            padding: 10px 12px;
            font: inherit;
        }

        textarea {
            min-height: 96px;
            resize: vertical;
        }

        button {
            width: fit-content;
            border: 0;
            border-radius: 6px;
            background: #2563eb;
            color: #ffffff;
            padding: 11px 18px;
            font: inherit;
            font-weight: 700;
            cursor: pointer;
        }

        .message {
            border-radius: 6px;
            margin-bottom: 16px;
            padding: 12px 14px;
        }

        .error {
            background: #fee2e2;
            color: #991b1b;
        }

        .success {
            background: #dcfce7;
            color: #166534;
        }

        .summary {
            margin-top: 18px;
            color: #475569;
        }
    </style>
</head>
<body>
<main>
    <h1>Thêm món ăn mới</h1>

    <%
        String error = (String) request.getAttribute("error");
        String success = (String) request.getAttribute("success");
        Object totalFoods = request.getAttribute("totalFoods");
        if (error != null) {
    %>
    <div class="message error"><%= error %></div>
    <%
        }
        if (success != null) {
    %>
    <div class="message success"><%= success %></div>
    <%
        }
    %>

    <form action="${pageContext.request.contextPath}/foods" method="post" enctype="multipart/form-data">
        <label>
            Tên món ăn
            <input type="text" name="name" required>
        </label>

        <label>
            Danh mục
            <select name="category" required>
                <option value="Khai vị">Khai vị</option>
                <option value="Món chính">Món chính</option>
                <option value="Đồ uống">Đồ uống</option>
                <option value="Tráng miệng">Tráng miệng</option>
                <option value="Món ăn kèm">Món ăn kèm</option>
            </select>
        </label>

        <label>
            Giá tiền
            <input type="number" name="price" min="0" step="0.01" required>
        </label>

        <label>
            Mô tả
            <textarea name="description"></textarea>
        </label>

        <label>
            Hình ảnh
            <input type="file" name="image" accept="image/*" required>
        </label>

        <button type="submit">Thêm món</button>
    </form>

    <p class="summary">Tổng số món hiện có: <%= totalFoods == null ? 0 : totalFoods %></p>
</main>
</body>
</html>
