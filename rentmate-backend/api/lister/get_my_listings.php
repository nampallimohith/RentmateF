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

try {
    // 1. Get Lister ID
    $stmt = $conn->prepare("SELECT id FROM users WHERE phone = ?");
    $stmt->execute([$phone]);
    $lister = $stmt->fetch(PDO::FETCH_ASSOC);

    // 2. Fetch all listings for this lister
    $stmt = $conn->prepare("SELECT * FROM listings WHERE lister_id = ? ORDER BY created_at DESC");
    $stmt->execute([$lister['id']]);
    $listings = $stmt->fetchAll(PDO::FETCH_ASSOC);

    sendResponse("success", "Listings fetched", $listings);
} catch (Exception $e) {
    sendResponse("error", "Failed to fetch listings: " . $e->getMessage(), null, 500);
}
?>
