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

if (!validateInput($data, ['title', 'price', 'location'])) {
    sendResponse("error", "Missing required listing fields", null, 400);
}

try {
    // 1. Get Lister ID
    $stmt = $conn->prepare("SELECT id FROM users WHERE phone = ?");
    $stmt->execute([$phone]);
    $lister = $stmt->fetch(PDO::FETCH_ASSOC);

    // 2. Insert Listing
    $sql = "INSERT INTO listings (lister_id, title, price, location, bhk_type, status, created_at) 
            VALUES (?, ?, ?, ?, ?, 'Active', NOW())";
    $stmt = $conn->prepare($sql);
    $stmt->execute([
        $lister['id'],
        $data->title,
        $data->price,
        $data->location,
        $data->bhk_type ?? '1BHK'
    ]);

    sendResponse("success", "Listing created successfully", ["listing_id" => $conn->lastInsertId()]);
} catch (Exception $e) {
    sendResponse("error", "Failed to create listing: " . $e->getMessage(), null, 500);
}
?>
