<?php
include_once '../../includes/db_connect.php';
include_once '../../includes/functions.php';
include_once '../../includes/jwt_helper.php';

// Auth Check
$headers = getallheaders();
$token = isset($headers['Authorization']) ? str_replace('Bearer ', '', $headers['Authorization']) : null;
$phone = validateJWT($token);

if (!$phone) {
    sendResponse("error", "Unauthorized access", null, 401);
}

$data = json_decode(file_get_contents("php://input"));

try {
    // Dynamically build update query based on provided fields
    $updates = [];
    $params = [];
    
    $allowed_fields = ['name', 'email', 'occupation', 'gender', 'bio'];
    foreach ($allowed_fields as $field) {
        if (isset($data->$field)) {
            $updates[] = "$field = ?";
            $params[] = $data->$field;
        }
    }
    
    if (empty($updates)) {
        sendResponse("error", "No fields provided for update", null, 400);
    }
    
    $params[] = $phone;
    $sql = "UPDATE users SET " . implode(", ", $updates) . " WHERE phone = ?";
    $stmt = $conn->prepare($sql);
    $stmt->execute($params);

    sendResponse("success", "Profile updated successfully");
} catch (Exception $e) {
    sendResponse("error", "Failed to update profile: " . $e->getMessage(), null, 500);
}
?>
