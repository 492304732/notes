# HTTPs

HTTP 有以下安全性问题：

1. 使用明文进行通信，内容可能会被窃听；
2. 不验证通信方的身份，通信方的身份有可能遭遇伪装；
3. 无法证明报文的完整性，报文有可能遭篡改。

HTTPs 并不是新协议，而是 HTTP 先和 SSL（Secure Sockets Layer）通信，再由 SSL 和 TCP 通信。也就是说 HTTPs 使用了隧道进行通信。

通过使用 SSL，HTTPs 具有了加密、认证和完整性保护。

![ssl-offloading.jpg](https://upload-images.jianshu.io/upload_images/1341067-347fad1d3f8be592.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## 加密

### 1. 对称密钥加密

对称密钥加密（Symmetric-Key Encryption），加密的加密和解密使用同一密钥。

- 优点：运算速度快；
- 缺点：密钥容易被获取。

![7fffa4b8-b36d-471f-ad0c-a88ee763bb76.png](https://upload-images.jianshu.io/upload_images/1341067-b5aae6af44bfa001.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


### 2. 公开密钥加密

公开密钥加密（Public-Key Encryption），也称为非对称密钥加密，使用一对密钥用于加密和解密，分别为公开密钥和私有密钥。公开密钥所有人都可以获得，通信发送方获得接收方的公开密钥之后，就可以使用公开密钥进行加密，接收方收到通信内容后使用私有密钥解密。

- 优点：更为安全；
- 缺点：运算速度慢；

![39ccb299-ee99-4dd1-b8b4-2f9ec9495cb4.png](https://upload-images.jianshu.io/upload_images/1341067-788f62d1bc2c52b2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


### 3. HTTPs 采用的加密方式

HTTPs 采用混合的加密机制，使用公开密钥加密用于传输对称密钥，之后使用对称密钥加密进行通信。（下图中的 Session Key 就是对称密钥）

![How-HTTPS-Works.png](https://upload-images.jianshu.io/upload_images/1341067-dd0286506382b2c8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 认证

通过使用  **证书**  来对通信方进行认证。

数字证书认证机构（CA，Certificate Authority）是客户端与服务器双方都可信赖的第三方机构。服务器的运营人员向 CA 提出公开密钥的申请，CA 在判明提出申请者的身份之后，会对已申请的公开密钥做数字签名，然后分配这个已签名的公开密钥，并将该公开密钥放入公开密钥证书后绑定在一起。

进行 HTTPs 通信时，服务器会把证书发送给客户端，客户端取得其中的公开密钥之后，先进行验证，如果验证通过，就可以开始通信。

使用 OpenSSL 这套开源程序，每个人都可以构建一套属于自己的认证机构，从而自己给自己颁发服务器证书。浏览器在访问该服务器时，会显示“无法确认连接安全性”或“该网站的安全证书存在问题”等警告消息。

![mutualssl_small.png](https://upload-images.jianshu.io/upload_images/1341067-5c14e12e7931b4d2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


## 完整性

SSL 提供报文摘要功能来验证完整性。