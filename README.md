FitRehberim Backend - Biraz Çaba, Çokça Kod

Merhaba, ben Can. Bu proje, biraz sabır, biraz kahve ve çokça debug konsolu ile ortaya çıktı. Fitness uygulamalarının hep aynı kalıplarda programlar sunduğunu görüp, "Ben olsam nasıl yaparım?" diye düşünerek başladığım bir serüven.

🤔 Neden Bu Projeyi Yaptım?

Aslında basit bir fikirle başladı: Herkesin vücudu, hedefi, zamanı farklı. Peki neden herkese aynı fitness programı veriliyor? Kendim de spor yaparken bunu hissettiğim için, gerçekten kişiselleştirilmiş bir sistem yapmaya karar verdim.

🎢 Geliştirme Macerası

İlk Gün: "Bu kolay olur"

Spring Boot'u açtım, birkaç endpoint yazdım. "Tamam," dedim, "birkaç saatte biter." Yanılmışım.

Lombok Kabusu

Kod yazarken sürekli getter/setter yazmaktan sıkıldığım için Lombok ekledim. Sonra VS Code'un Lombok'u tanımadığını fark ettim. 2 saatimi sadece @Data annotation'ının neden çalışmadığını anlamaya çalışarak geçirdim.

Aldığım hata: cannot find symbol: method getEmail()

Çözüm: VS Code'a Lombok extension'ı yüklemek, Maven'ı temizlemek, bilgisayarı yeniden başlatmak (en etkilisi bu oldu).

Paket İsimleri Fiyaskosu

Tüm Java dosyalarımda package main.java.com.fitrehberim... yazıyordu. Spring Boot bunu anlamadı tabii. Tek tek 23 dosyanın package ismini düzelttim. Ellerim ağrıdı ama öğrendim: Spring Boot paket yapısına çok hassas.

JWT ile İmtihanım

Spring Security biliyordum ama JWT ile entegre etmek bambaşka bir seviye. Token oluşturma, validation, filter chain... Her şey çalıştığında hissettiğim mutluluğu anlatamam.

🔧 Neler Çalışıyor?

Kayıt Ol - Email ve şifre ile hesap açıyorsun
Giriş Yap - Token alıyorsun
Profil Gör - Kendi bilgilerini görüyorsun
Program Oluştur - "Bana özel bir program yap" diyorsun
Programı Gör - Haftalık planını görüyorsun
En sevdiğim kısım: WorkoutGenerator sınıfı. Burada kullanıcının:

Hedefine göre (kilo verme, kas yapma, koruma) antrenman tipi seçiyor
Fitness seviyesine göre (başlangıç, orta, ileri) süre ayarlıyor
Müsait günlerine göre haftalık program oluşturuyor
Fiziksel özelliklerine göre yakılan kaloriyi hesaplıyor

🐛 Yaşadığım Enteresan Bug'lar

Port 8080 Meşgul

Uygulamayı çalıştırdım, "Port 8080 zaten kullanımda" dedi. Meğersem başka bir Spring Boot projesi daha çalışıyormuş arka planda. application.properties dosyası oluşturup server.port=8081 yazdım.

Token Çalışmıyor

Frontend'den token gönderiyorum ama backend "Yetkin yok" diyor. Meğersem header'da Authorization: Bearer token şeklinde göndermem gerekiyormuş, ben sadece token'ı gönderiyormuşum.

🚀 Nasıl Çalıştırırsın?

Java 17 yüklü olmalı
Terminali aç
Şu komutları sırayla gir:
bash
git clone https://github.com/CanErkilinc/fit-rehberim-backend.git
cd fit-rehberim-backend
mvn spring-boot:run
Tarayıcından http://localhost:8080'e git (aslında API olduğu için bir şey göremezsin, normal)
Postman veya curl ile test et:
bash
curl -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d '{
  "email": "ornek@email.com",
  "password": "sifre123",
  "height": 175,
  "weight": 70,
  "activityLevel": "MEDIUM",
  "fitnessLevel": "BEGINNER", 
  "goal": "LOSE_WEIGHT",
  "availableDays": ["Pazartesi", "Salı", "Perşembe"],
  "availableTime": "18:00"
}'
📚 Öğrendiklerim

Spring Boot gerçekten hızlı - Production-ready uygulama yapmak çok daha kolay
JWT authentication nasıl çalışır
H2 database ile hızlı prototipleme
RESTful API tasarımı best practices
Debug yapma sanatı - Loglara bakıp problemi bulmak
🤓 Teknik Detaylar (İlgilenenler İçin)

Java 17 - En son LTS versiyon
Spring Boot 3.2.0 - En güncel Spring
Spring Security 6 - JWT ile kimlik doğrulama
Spring Data JPA - Database işlemleri
H2 Database - Geliştirme sırasında in-memory DB
JJWT 0.12.3 - Token oluşturma ve doğrulama
Lombok - Daha az boilerplate kodu
Maven - Bağımlılık yönetimi
🎉 En Güzel An

Uygulama ilk çalıştığında ve BUILD SUCCESS yazısını gördüğümde, sonra da ilk API çağrısından {"token": "...", "message": "Registration successful"} response'unu aldığımda. O anki mutluluğu anlatamam.

🔮 Gelecek Planları

Frontend - React ile kullanıcı arayüzü yapacağım
Docker - Container'a alıp her yerde çalıştırabileceğim
PostgreSQL - Gerçek database'e geçeceğim
Unit Test - Test yazmayı öğreneceğim
CI/CD - Otomatik deploy yapacağım
💭 Son Söz

Bu projede en çok şunu öğrendim: Kod yazmak sadece syntax bilmek değil, problem çözmek. Her hata yeni bir şey öğrenme fırsatı. Şimdi frontend kısmına başlıyorum, orada da neler yaşayacağım merak ediyorum.

Eğer sen de backend öğreniyorsan veya böyle bir proje yapmak istiyorsan, soruların olursa çekinmeden sorabilirsin. Ben yolumun başındayım ama öğrendiklerimi paylaşmaktan mutluluk duyarım.

Not: Bu proje tamamen öğrenme amacıyla yapıldı. Tüm kodları inceleyebilir, kullanabilir, değiştirebilirsin. Eğer bir hata görürsen veya iyileştirme önerin olursa, pull request açmaktan çekinme!

Can Erkılınç
"Bir bug daha, bir öğrenme daha"
