-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th6 24, 2025 lúc 05:16 PM
-- Phiên bản máy phục vụ: 10.4.28-MariaDB
-- Phiên bản PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `phuonglinh_db`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `addressitems`
--

CREATE TABLE `addressitems` (
  `id` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `receiver` varchar(256) NOT NULL,
  `phone` varchar(256) NOT NULL,
  `province` varchar(256) NOT NULL,
  `district` varchar(256) NOT NULL,
  `village` varchar(256) NOT NULL,
  `detail` varchar(256) NOT NULL,
  `updateAt` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `addressitems`
--

INSERT INTO `addressitems` (`id`, `userID`, `receiver`, `phone`, `province`, `district`, `village`, `detail`, `updateAt`) VALUES
(9, 1, 'Nhatg drth', '113', 'Hồ Chí Minh', 'Thủ Đức', 'Bình Trưng Tây', '14 đường số 2', '2025-01-20 18:30:41');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cartitems`
--

CREATE TABLE `cartitems` (
  `id` int(11) NOT NULL,
  `productDetailID` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `updateTime` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `cartitems`
--

INSERT INTO `cartitems` (`id`, `productDetailID`, `qty`, `userID`, `updateTime`) VALUES
(49, 14, 1, 2, '2024-12-28 15:22:23'),
(55, 4, 1, 2, '2025-01-11 14:54:01'),
(63, 4, 2, 1, '2025-01-21 08:41:38'),
(64, 2, 1, 1, '2025-01-21 08:41:47'),
(65, 3, 1, 1, '2025-01-21 08:41:49'),
(66, 13, 1, 1, '2025-01-21 08:42:03'),
(67, 11, 2, 1, '2025-01-21 08:42:15');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `name` int(11) NOT NULL,
  `avai` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `comments`
--

CREATE TABLE `comments` (
  `id` int(11) NOT NULL,
  `productID` int(11) NOT NULL,
  `star` int(11) DEFAULT NULL,
  `userID` int(11) NOT NULL,
  `content` varchar(256) NOT NULL,
  `time` datetime DEFAULT current_timestamp(),
  `orderID` int(11) NOT NULL,
  `avai` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `comments`
--

INSERT INTO `comments` (`id`, `productID`, `star`, `userID`, `content`, `time`, `orderID`, `avai`) VALUES
(22, 3, 5, 1, '500g\nok', '2025-01-21 12:28:11', 27, 1),
(23, 3, 1, 1, '500g - 250g\nokok test 1 sao', '2025-01-21 12:29:00', 26, 1),
(24, 3, 2, 1, '250g\ntest 2sao', '2025-01-21 12:30:26', 28, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `employees`
--

CREATE TABLE `employees` (
  `id` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `position` varchar(20) DEFAULT NULL,
  `area` varchar(20) DEFAULT NULL,
  `updateAt` datetime NOT NULL DEFAULT current_timestamp(),
  `avai` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `images`
--

CREATE TABLE `images` (
  `id` int(11) NOT NULL,
  `title` varchar(256) DEFAULT NULL,
  `url` varchar(256) NOT NULL,
  `productID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `images`
--

INSERT INTO `images` (`id`, `title`, `url`, `productID`) VALUES
(6, 'banner', 'banner2.jpg', 3),
(7, 'banner', 'banner3.jpg', 3),
(16, NULL, 'default.jpg', 2),
(17, NULL, 'banner1.jpg', 1),
(18, NULL, 'banner2.jpg', 1),
(19, NULL, 'Screenshot 2024-09-25 131631.png', 4),
(20, NULL, 'Screenshot 2024-09-25 131717.png', 4),
(21, NULL, 'Screenshot 2024-09-25 131746.png', 4),
(22, NULL, 'Screenshot 2024-09-25 131631.png', 5),
(23, NULL, 'Screenshot 2024-09-25 131717.png', 5),
(24, NULL, 'Screenshot 2024-09-25 131746.png', 5),
(25, NULL, 'Screenshot 2024-09-25 131631.png', 6),
(26, NULL, 'Screenshot 2024-09-25 131717.png', 6),
(27, NULL, 'Screenshot 2024-09-25 131746.png', 6),
(28, NULL, 'Screenshot 2024-09-25 131631.png', 7),
(29, NULL, 'Screenshot 2024-09-25 131717.png', 7),
(30, NULL, 'Screenshot 2024-09-25 131746.png', 7),
(31, NULL, 'Screenshot 2024-09-25 131717.png', 8),
(32, NULL, 'Screenshot 2024-09-25 131746.png', 8),
(33, NULL, 'Screenshot 2024-09-25 131717.png', 9),
(34, NULL, 'Screenshot 2024-09-25 131746.png', 9),
(35, NULL, 'Screenshot 2024-09-26 214756.png', 9),
(36, NULL, 'default.jpg', 10),
(37, NULL, 'default.jpg', 11),
(38, NULL, 'default.jpg', 12),
(39, NULL, 'default.jpg', 13),
(40, NULL, 'default.jpg', 14),
(41, NULL, 'default.jpg', 15);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `interface_images`
--

CREATE TABLE `interface_images` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `kind` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `interface_images`
--

INSERT INTO `interface_images` (`id`, `title`, `url`, `kind`) VALUES
(19, 'Untitled-2.png', 'Untitled-2.png', 'BANNER'),
(20, 'Untitled-3.png', 'Untitled-3.png', 'BANNER'),
(21, 'Untitled-4.png', 'Untitled-4.png', 'BANNER'),
(28, 'logo_tl_2024.webp', 'logo_tl_2024.webp', 'LOGO');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `logs`
--

CREATE TABLE `logs` (
  `id` int(11) NOT NULL,
  `ip` varchar(250) NOT NULL,
  `info` text NOT NULL,
  `action` text NOT NULL,
  `preValue` text NOT NULL,
  `afterValue` text NOT NULL,
  `area` varchar(255) NOT NULL,
  `level` int(11) NOT NULL,
  `time` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `mail_configs`
--

CREATE TABLE `mail_configs` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `pwd` varchar(255) NOT NULL,
  `avai` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `mail_configs`
--

INSERT INTO `mail_configs` (`id`, `email`, `pwd`, `avai`) VALUES
(1, '2003tonhat@gmail.com', 'I%#^ +[#} [%c8 =<{A', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orderdetails`
--

CREATE TABLE `orderdetails` (
  `id` int(11) NOT NULL,
  `orderID` int(11) NOT NULL,
  `productDetailID` int(11) NOT NULL,
  `productName` varchar(256) NOT NULL,
  `detailClassify` varchar(256) DEFAULT NULL,
  `qty` int(11) NOT NULL,
  `saleProgramID` int(11) DEFAULT NULL,
  `priceUnit` int(11) NOT NULL,
  `isComment` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `orderdetails`
--

INSERT INTO `orderdetails` (`id`, `orderID`, `productDetailID`, `productName`, `detailClassify`, `qty`, `saleProgramID`, `priceUnit`, `isComment`) VALUES
(26, 25, 4, 'Nấm Linh Chi (xích chi)', '500g', 5, NULL, 249000, 0),
(27, 26, 4, 'Nấm Linh Chi (xích chi)', '500g', 2, NULL, 249000, 0),
(28, 26, 5, 'Nấm Linh Chi (xích chi)', '250g', 3, NULL, 159000, 0),
(29, 27, 4, 'Nấm Linh Chi (xích chi)', '500g', 1, NULL, 249000, 0),
(30, 27, 2, 'Đông trùng sấy thăng hoa ', 'hũ 10g', 1, NULL, 99000, 0),
(31, 27, 14, 'To Minh Nhat cuoi cung upate lan cuoi cung cung', 'hũ 10g', 1, NULL, 124, 0),
(32, 28, 5, 'Nấm Linh Chi (xích chi)', '250g', 3, NULL, 159000, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `totalMoney` double NOT NULL,
  `shippingFee` double NOT NULL,
  `discountVoucher` double NOT NULL,
  `createAt` datetime NOT NULL DEFAULT current_timestamp(),
  `updateAt` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `userID` int(11) NOT NULL,
  `receiverInfo` varchar(256) NOT NULL,
  `isPayed` int(11) NOT NULL DEFAULT 0,
  `status` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `orders`
--

INSERT INTO `orders` (`id`, `totalMoney`, `shippingFee`, `discountVoucher`, `createAt`, `updateAt`, `userID`, `receiverInfo`, `isPayed`, `status`) VALUES
(25, 1245000, 0, 0, '2025-01-20 18:31:35', '2025-05-01 16:05:10', 1, 'Nhatg drth (113)   |Hồ Chí Minh - Thủ Đức - Bình Trưng Tây - 14 đường số 2', 0, 11),
(26, 975000, 0, 0, '2025-01-21 00:17:55', '2025-01-21 11:28:27', 1, 'Nhatg drth (113)   |Hồ Chí Minh, Thủ Đức, Bình Trưng Tây, 14 đường số 2', 0, 3),
(27, 348124, 0, 0, '2025-01-21 00:27:09', '2025-01-21 11:28:25', 1, 'Nhatg drth (113)   |Hồ Chí Minh, Thủ Đức, Bình Trưng Tây, 14 đường số 2', 0, 3),
(28, 477000, 0, 0, '2025-01-21 12:29:18', '2025-05-01 16:05:13', 1, 'Nhatg drth (113)   |Hồ Chí Minh, Thủ Đức, Bình Trưng Tây, 14 đường số 2', 0, 11);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `productdetails`
--

CREATE TABLE `productdetails` (
  `id` int(11) NOT NULL,
  `productID` int(11) NOT NULL,
  `classify` varchar(20) NOT NULL,
  `price` double NOT NULL DEFAULT 0,
  `prePrice` double NOT NULL DEFAULT 0,
  `qty` int(11) NOT NULL DEFAULT 0,
  `avai` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `productdetails`
--

INSERT INTO `productdetails` (`id`, `productID`, `classify`, `price`, `prePrice`, `qty`, `avai`) VALUES
(1, 1, '200g', 60000, 100000, 0, -1),
(2, 2, 'hũ 10g', 99000, 120000, 998, 1),
(3, 2, 'hũ 20g', 179000, 200000, 97, 1),
(4, 3, '500g', 249000, 300000, 4, 1),
(5, 3, '250g', 159000, 200000, 90, 1),
(6, 2, 'gaerga', 262346, 25346, 2346, -99),
(7, 2, 'gertgae', 24362, 5345246, 46, -99),
(8, 9, 'hũ 10g', 1234, 32151, 23, 1),
(9, 9, 'văef231', 1324, 1234, 1234, 1),
(10, 10, 'hũ 10g', 4234, 123, 124, 1),
(11, 11, 'hũ 10g', 2134, 414, 1234, 1),
(12, 12, 'hũ 10g', 1234, 41234, 234, 1),
(13, 13, 'hũ 10g', 132, 414, 1324, 1),
(14, 14, 'hũ 10g', 124, 12, 121, 1),
(15, 15, 'hũ 10g', 15345, 41235, 135, 1),
(16, 1, '100r', 1234512, 132124, 1234, 1),
(17, 1, '142351', 135135, 3515, 1325, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `name` varchar(256) NOT NULL,
  `ingredient` varchar(256) DEFAULT NULL,
  `category` varchar(20) NOT NULL,
  `description` text DEFAULT NULL,
  `direction` text DEFAULT NULL,
  `thumbnail` varchar(256) NOT NULL,
  `prominence` int(11) NOT NULL DEFAULT 0,
  `star` double NOT NULL DEFAULT 0,
  `avai` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `products`
--

INSERT INTO `products` (`id`, `name`, `ingredient`, `category`, `description`, `direction`, `thumbnail`, `prominence`, `star`, `avai`) VALUES
(1, 'Đông trùng tươi ', 'gạo lức, dưỡng chất cần thiết', 'dongtrung', '+ Đông trùng hạ thảo tươi là nấm còn giữ nguyên nước và các thành phần dưỡng chất. Theo nhiều đánh giá, trùng thảo tươi sẽ có nhiều tác dụng tích cực. Vậy đông trùng hạ thảo tươi có đặc điểm như thế nào, công dụng và cách dùng ra sao? Tất cả sẽ được chia sẻ đến bạn đọc qua những nội dung dưới đây.\r\n\r\n+ Đặc điểm nhận dạng và thành phần hóa học đông trùng hạ thảo tươi\r\nĐông trùng hạ thảo tươi có các đặc điểm nhận dạng rất rõ ràng, khó bị nhầm lẫn. Loại thảo dược này cũng có thành phần hóa học rất đa dạng, tốt cho sức khỏe.\r\n+ 10 tác dụng của đông trùng hạ thảo tươi\r\n* Phục hồi suy nhược, bồi bổ cơ thể\r\n* Tăng cường hệ miễn dịch\r\n* Tăng cường sức khỏe cho hệ tim mạch\r\n* Hỗ trợ điều trị một số bệnh lý về gan\r\n* Trùng thảo giúp tăng cường cho hệ bài tiết\r\n* Tốt cho phổi, hệ hô hấp\r\n* Hỗ trợ khắc phục bệnh tiểu đường\r\n* Cải thiện chức năng sinh lý\r\n* Làm đẹp da, làm chậm lão hóa\r\n* Hỗ trợ ngăn ngừa ung thư\r\n', '+ Hướng dẫn cách dùng nấm đông trùng hạ thảo tươi\r\nĐể sử dụng loại dược liệu quý này chúng ta có rất nhiều cách khác nhau. Dù là pha trà, nấu cháo, nấu canh hay ngâm rượu, ngâm mật ong đều cho tác dụng tốt. Bạn có thể tham khảo một số cách sử dụng trùng thảo tươi sau đây:\r\n* Ngâm mật ong / Trùng thảo ngâm rượu / Dùng trùng thảo tươi pha trà / Nấu cháo / Nấu canh bằng trùng thảo tươi\r\n', 'Screenshot 2024-09-28 005747.png', 1, 0, 1),
(2, 'Đông trùng sấy thăng hoa ', '100% đông trùng tươi sấy thăng hoa', 'dongtrung', '- Mô tả sản phẩm\r\n+ Đông trùng hạ thảo sấy thăng hoa là sản phẩm được tạo ra bằng cách loại bỏ nước trong đông trùng hạ thảo tươi thông qua phương pháp sấy thăng hoa.\r\n+ Quá trình này giúp loại bỏ gần như toàn bộ lượng nước trong đông trùng hạ thảo (chỉ còn khoảng 3-5%) mà không làm biến đổi cấu trúc, màu sắc, hương vị và đặc biệt là hàm lượng dinh dưỡng quý giá.\r\n\r\n\r\n+ Ưu điểm vượt trội của đông trùng hạ thảo sấy thăng hoa\r\nSấy thăng hoa được xem là phương pháp sấy khô hiện đại và tối ưu nhất hiện nay, mang đến nhiều ưu điểm vượt trội so với các phương pháp sấy khô truyền thống như sấy nhiệt, sấy lạnh.\r\n*Bảo Toàn Hàm Lượng Dinh Dưỡng\r\n* Giữ Nguyên Hương Vị, Màu Sắc & Hình Dạng\r\n* Kéo Dài Thời Gian Bảo Quản\r\n* Thuận Tiện Cho Vận Chuyển & Bảo Quản\r\n* Dễ Dàng Sử Dụng & Chế Biến\r\n\r\n+ Tác dụng của đông trùng hạ thảo sấy thăng hoa đối với sức khỏe\r\n* Phục hồi suy nhược, bồi bổ cơ thể\r\n* Tăng cường hệ miễn dịch\r\n* Tăng cường sức khỏe cho hệ tim mạch\r\n* Hỗ trợ điều trị một số bệnh lý về gan\r\n* Trùng thảo giúp tăng cường cho hệ bài tiết\r\n* Tốt cho phổi, hệ hô hấp\r\n* Hỗ trợ khắc phục bệnh tiểu đường\r\n* Cải thiện chức năng sinh lý\r\n* Làm đẹp da, làm chậm lão hóa\r\n* Hỗ trợ ngăn ngừa ung thư\r\n', '+ Hướng dẫn cách dùng nấm đông trùng hạ thảo sấy thăng hoa\r\nĐể sử dụng loại dược liệu quý này chúng ta có rất nhiều cách khác nhau. Dù là pha trà, nấu cháo, nấu canh hay ngâm rượu, ngâm mật ong đều cho tác dụng tốt. Bạn có thể tham khảo một số cách sử dụng trùng thảo tươi sau đây:\r\n* Ngâm mật ong / Trùng thảo ngâm rượu / Dùng trùng thảo pha trà / Nấu cháo / Nấu canh bằng trùng thảo\r\n\r\n+ HSD 24 tháng\r\n', 'default.jpg', 103, 0, -1),
(3, 'Nấm Linh Chi (xích chi)', NULL, 'nam', '- Nấm xích chi là một loại dược liệu quý xuất hiện cách đây hàng nghìn năm. Theo nghiên cứu lâm sàng nấm xích chi mang lại nhiều tác dụng cho sức khỏe.\r\n\r\n+ Tác dụng của nấm xích chi\r\nNấm linh chi hay nấm xích chi có tác dụng gì? Đây luôn là thắc mắc của những người lần đầu sử dụng sản phẩm. Theo một số nghiên cứu trước đó cho hay những trường hợp sử dụng nấm xích chi cải thiện vấn đề như căng thẳng, mất ngủ,... Ngoài ra, nấm xích chi còn mang lại tác dụng đến sức khỏe như:\r\n•	Ổn định huyết áp\r\n•	Cân bằng chỉ số cholesterol\r\n•	Hạn chế nguy cơ mắc bệnh tim mạch\r\n•	Tăng cường chức năng cho gan và thận\r\n•	Phòng ngừa bệnh đường hô hấp như hen suyễn.\r\n•	Giảm nguy cơ mắc cúm do vi khuẩn xâm nhập\r\n•	Đẩy lùi một số căn bệnh thế kỷ nguy hiểm\r\n•	Hỗ trợ trong điều trị ung thư\r\n•	Giảm đau và ngăn chặn nguy cơ tái nhiễmzona\r\n•	Tạo hệ thống miễn dịch vững chắc cho người bệnh\r\n•	Giảm căng thẳng mệt mỏi\r\n\r\n', '+ Hướng dẫn cách dùng nấm xích chi\r\nĐể sử dụng loại dược liệu quý này chúng ta có rất nhiều cách khác nhau. Dù là pha trà, ngâm rượu, ngâm mật ong đều cho tác dụng tốt. \r\n', 'banner.jpg', 46, 0, 1),
(4, 'To Minh Nhat', 'vaeger', 'dinhduong', 'rfg', 'qe', 'Screenshot 2024-09-25 131606.png', 99, 0, 1),
(5, 'To Minh Nhat', 'vaeger', 'dongtrung', 'rfg', 'qe', 'Screenshot 2024-09-25 131606.png', 99, 0, 1),
(6, 'To Minh Nhat', 'vaeger', 'chao', 'rfg', 'qe', 'Screenshot 2024-09-25 131606.png', 99, 0, 1),
(7, 'To Minh Nhat', 'vaeger', 'dongtrung', 'rfg', 'qe', 'Screenshot 2024-09-25 131606.png', 99, 0, 1),
(8, 'To Minh Nhat', '', 'dongtrung', '', '', 'Screenshot 2024-09-25 131606.png', 99, 0, 1),
(9, 'To Minh Nhat', '', 'dongtrung', '243trew', 'eger', 'Screenshot 2024-09-25 131606.png', 99, 0, 1),
(10, 'To Minh Nhat', 'fger', 'dongtrung', '', '', '', 99, 0, 1),
(11, 'To Minh Nhat', '', 'dongtrung', '', '', 'default.jpg', 100, 0, 1),
(12, 'To Minh Nhat', '', 'dongtrung', '', '', 'default.jpg', 99, 0, 1),
(13, 'To Minh Nhat', '', 'dongtrung', '', '', 'default.jpg', 100, 0, 1),
(14, 'To Minh Nhat cuoi cung upate lan cuoi cung cung', 'fewfs', 'nam', '', '', 'default.jpg', 4, 0, 1),
(15, 'cuo i cung cung ', '', 'dongtrung', '', '', 'default.jpg', 0, 0, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `revenues`
--

CREATE TABLE `revenues` (
  `id` int(11) NOT NULL,
  `date` datetime NOT NULL DEFAULT current_timestamp(),
  `money` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `saleprograms`
--

CREATE TABLE `saleprograms` (
  `id` int(11) NOT NULL,
  `productIDs` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`productIDs`)),
  `title` varchar(256) NOT NULL,
  `content` varchar(256) NOT NULL,
  `discount` int(11) NOT NULL,
  `kind` int(11) NOT NULL,
  `startTime` datetime NOT NULL,
  `endTime` datetime NOT NULL,
  `avai` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(256) NOT NULL,
  `email` varchar(256) NOT NULL,
  `pwd` varchar(256) NOT NULL,
  `phone` varchar(256) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `gender` int(11) DEFAULT NULL,
  `roles` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`roles`)),
  `dateIn` datetime NOT NULL DEFAULT current_timestamp(),
  `avai` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `pwd`, `phone`, `dob`, `gender`, `roles`, `dateIn`, `avai`) VALUES
(1, 'To Nhat', 'admin@gmail.com', '81dc9bdb52d04dc20036dbd8313ed055', '114', '2003-01-02', 0, '[\"customer\",\"employee\",\"product\",\"order\",\"interface\"]', '2024-12-19 00:00:00', 1),
(2, 'To Minh Nhat', 'khachhang@gmail.com', '81dc9bdb52d04dc20036dbd8313ed055', '23842167412', '2003-01-02', 1, NULL, '2024-12-19 00:00:00', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `verifycodes`
--

CREATE TABLE `verifycodes` (
  `id` int(11) NOT NULL,
  `code` varchar(20) NOT NULL,
  `email` varchar(250) NOT NULL,
  `time` datetime NOT NULL DEFAULT current_timestamp(),
  `isVerify` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `vouchers`
--

CREATE TABLE `vouchers` (
  `id` varchar(20) NOT NULL,
  `productIDs` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`productIDs`)),
  `userIDs` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`userIDs`)),
  `discount` int(11) NOT NULL,
  `maxMoney` double DEFAULT NULL,
  `qty` int(11) NOT NULL,
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `description` varchar(256) NOT NULL,
  `avai` int(11) NOT NULL DEFAULT 1,
  `createAt` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `vouchers`
--

INSERT INTO `vouchers` (`id`, `productIDs`, `userIDs`, `discount`, `maxMoney`, `qty`, `startTime`, `endTime`, `description`, `avai`, `createAt`) VALUES
('max100', '[1,2,3]', NULL, 50, 100000, 4, '2024-12-14 10:21:18', '2024-12-16 10:22:14', 'test thoi', 1, '2025-01-07 22:44:15'),
('test100', '[1,2,3]', NULL, 40, NULL, 0, NULL, NULL, 'test thoi', 1, '2025-01-07 22:44:15'),
('test100', '[1,2,3,4]', NULL, 50, 100000, 100, NULL, NULL, 'test thoi', 1, '2025-01-11 00:40:34'),
('test200', '[1,2,3,4]', NULL, 50, 100000, 100, NULL, NULL, 'test thoi', -1, '2025-01-11 00:40:58'),
('test300', '[1,2,3,4]', NULL, 50, 100000, 100, NULL, NULL, 'test thoi', -99, '2025-01-11 00:40:58'),
('test400', '[1,2,3,4]', NULL, 50, 100000, 100, NULL, NULL, 'test thoi', -1, '2025-01-11 00:40:58'),
('test500', '[1,2,3,4]', NULL, 500, 100000, 100, NULL, NULL, 'test thoigrre', -99, '2025-01-11 00:40:58');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `warehouses`
--

CREATE TABLE `warehouses` (
  `id` int(11) NOT NULL,
  `action` int(11) NOT NULL,
  `productDetailID` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `addressitems`
--
ALTER TABLE `addressitems`
  ADD PRIMARY KEY (`id`),
  ADD KEY `frk_addressItem_user` (`userID`);

--
-- Chỉ mục cho bảng `cartitems`
--
ALTER TABLE `cartitems`
  ADD PRIMARY KEY (`id`),
  ADD KEY `frk_cart_userID` (`userID`),
  ADD KEY `frk_cart_productDetailID` (`productDetailID`);

--
-- Chỉ mục cho bảng `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `frk_userID` (`userID`);

--
-- Chỉ mục cho bảng `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`id`),
  ADD KEY `frk_employees_userID` (`userID`);

--
-- Chỉ mục cho bảng `images`
--
ALTER TABLE `images`
  ADD PRIMARY KEY (`id`),
  ADD KEY `frk_image_product` (`productID`);

--
-- Chỉ mục cho bảng `interface_images`
--
ALTER TABLE `interface_images`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `logs`
--
ALTER TABLE `logs`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `mail_configs`
--
ALTER TABLE `mail_configs`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `orderdetails`
--
ALTER TABLE `orderdetails`
  ADD PRIMARY KEY (`id`),
  ADD KEY `frk_orderDetail_order` (`orderID`),
  ADD KEY `frk_orderDetail_productdetail` (`productDetailID`);

--
-- Chỉ mục cho bảng `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `frk_order_userID` (`userID`);

--
-- Chỉ mục cho bảng `productdetails`
--
ALTER TABLE `productdetails`
  ADD PRIMARY KEY (`id`),
  ADD KEY `frk_productID` (`productID`);

--
-- Chỉ mục cho bảng `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `revenues`
--
ALTER TABLE `revenues`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `saleprograms`
--
ALTER TABLE `saleprograms`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `verifycodes`
--
ALTER TABLE `verifycodes`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `warehouses`
--
ALTER TABLE `warehouses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `frk_warehouse_user` (`userID`),
  ADD KEY `frk_warehouse_productDetail` (`productDetailID`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `addressitems`
--
ALTER TABLE `addressitems`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT cho bảng `cartitems`
--
ALTER TABLE `cartitems`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=68;

--
-- AUTO_INCREMENT cho bảng `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `comments`
--
ALTER TABLE `comments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT cho bảng `employees`
--
ALTER TABLE `employees`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT cho bảng `images`
--
ALTER TABLE `images`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT cho bảng `interface_images`
--
ALTER TABLE `interface_images`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT cho bảng `logs`
--
ALTER TABLE `logs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1647;

--
-- AUTO_INCREMENT cho bảng `mail_configs`
--
ALTER TABLE `mail_configs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `orderdetails`
--
ALTER TABLE `orderdetails`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT cho bảng `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT cho bảng `productdetails`
--
ALTER TABLE `productdetails`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT cho bảng `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT cho bảng `revenues`
--
ALTER TABLE `revenues`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `saleprograms`
--
ALTER TABLE `saleprograms`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=226;

--
-- AUTO_INCREMENT cho bảng `verifycodes`
--
ALTER TABLE `verifycodes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT cho bảng `warehouses`
--
ALTER TABLE `warehouses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `addressitems`
--
ALTER TABLE `addressitems`
  ADD CONSTRAINT `frk_addressItem_user` FOREIGN KEY (`userID`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `cartitems`
--
ALTER TABLE `cartitems`
  ADD CONSTRAINT `frk_cart_productDetailID` FOREIGN KEY (`productDetailID`) REFERENCES `productdetails` (`id`),
  ADD CONSTRAINT `frk_cart_userID` FOREIGN KEY (`userID`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `frk_userID` FOREIGN KEY (`userID`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `employees`
--
ALTER TABLE `employees`
  ADD CONSTRAINT `frk_employees_userID` FOREIGN KEY (`userID`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `images`
--
ALTER TABLE `images`
  ADD CONSTRAINT `frk_image_product` FOREIGN KEY (`productID`) REFERENCES `products` (`id`);

--
-- Các ràng buộc cho bảng `orderdetails`
--
ALTER TABLE `orderdetails`
  ADD CONSTRAINT `frk_orderDetail_order` FOREIGN KEY (`orderID`) REFERENCES `orders` (`id`),
  ADD CONSTRAINT `frk_orderDetail_productdetail` FOREIGN KEY (`productDetailID`) REFERENCES `productdetails` (`id`);

--
-- Các ràng buộc cho bảng `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `frk_order_userID` FOREIGN KEY (`userID`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `productdetails`
--
ALTER TABLE `productdetails`
  ADD CONSTRAINT `frk_productID` FOREIGN KEY (`productID`) REFERENCES `products` (`id`);

--
-- Các ràng buộc cho bảng `warehouses`
--
ALTER TABLE `warehouses`
  ADD CONSTRAINT `frk_warehouse_productDetail` FOREIGN KEY (`productDetailID`) REFERENCES `productdetails` (`id`),
  ADD CONSTRAINT `frk_warehouse_user` FOREIGN KEY (`userID`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
