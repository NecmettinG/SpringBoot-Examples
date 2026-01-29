# =============================================
# KONYA DISTRICTS AND NEIGHBOURHOODS DATA INSERTION SCRIPT
# =============================================
# This script inserts all Konya (Turkey) districts and their neighbourhoods
# via POST requests to the Spring Boot API
#
# API Endpoints:
#   POST /api/districts - Create district
#   POST /api/districts/{id}/neighbourhoods - Create neighbourhood
# =============================================

$baseUrl = "http://localhost:8080/api/districts"

Write-Host "========================================" -ForegroundColor Magenta
Write-Host "Konya Districts and Neighbourhoods" -ForegroundColor Magenta
Write-Host "Data Insertion Script" -ForegroundColor Magenta
Write-Host "========================================" -ForegroundColor Magenta
Write-Host ""

# =============================================
# DISTRICTS DATA (31 Districts)
# =============================================
$districts = @(
    "Meram", "Selcuklu", "Karatay", "Eregli", "Aksehir",
    "Beysehir", "Cihanbeyli", "Cumra", "Derbent", "Derebucak",
    "Doganhisar", "Emirgazi", "Guneysinir", "Hadim", "Halkapinar",
    "Huyuk", "Ilgin", "Kadinhani", "Karapinar", "Kulu",
    "Sarayonu", "Seydisehir", "Taskent", "Tuzlukcu", "Yalihuyuk",
    "Yunak", "Ahirli", "Akoren", "Altinekin", "Bozkir", "Catalhuyuk"
)

# Create Districts
Write-Host "Creating Districts..." -ForegroundColor Yellow
$districtIds = @{}
foreach ($district in $districts) {
    try {
        $body = "{`"districtName`": `"$district`"}"
        $response = Invoke-RestMethod -Uri $baseUrl -Method POST -ContentType "application/json" -Body $body
        $districtIds[$district] = $response.id
        Write-Host "  Created: $district (ID: $($response.id))" -ForegroundColor Green
    } catch {
        Write-Host "  Skipped (exists): $district" -ForegroundColor Yellow
    }
}

# =============================================
# NEIGHBOURHOODS DATA
# =============================================
$neighbourhoodsData = @{
    "Meram" = @(
        @{name="Yenisehir"; lat=37.8600; lon=32.4500},
        @{name="Lalebahce"; lat=37.8550; lon=32.4400},
        @{name="Havzan"; lat=37.8350; lon=32.4200},
        @{name="Aymanas"; lat=37.8700; lon=32.4600},
        @{name="Godene"; lat=37.8400; lon=32.4300},
        @{name="Karakurt"; lat=37.8450; lon=32.4450},
        @{name="Akcesme"; lat=37.8500; lon=32.4350},
        @{name="Kozagac"; lat=37.8650; lon=32.4550},
        @{name="Harmancik"; lat=37.8300; lon=32.4100},
        @{name="Hasankoyde"; lat=37.8520; lon=32.4420}
    )
    "Selcuklu" = @(
        @{name="Bosna Hersek"; lat=37.9100; lon=32.4800},
        @{name="Yazir"; lat=37.9200; lon=32.4700},
        @{name="Sille"; lat=37.9400; lon=32.4200},
        @{name="Tepekoy"; lat=37.9050; lon=32.4850},
        @{name="Hocacihan"; lat=37.9000; lon=32.4750},
        @{name="Buyukkayacik"; lat=37.9350; lon=32.5100},
        @{name="Dikilitasi"; lat=37.9150; lon=32.4650},
        @{name="Ardicli"; lat=37.9080; lon=32.4600},
        @{name="Musalla"; lat=37.8950; lon=32.4700},
        @{name="Atakent"; lat=37.9250; lon=32.4500}
    )
    "Karatay" = @(
        @{name="Fevzi Cakmak"; lat=37.8750; lon=32.5100},
        @{name="Mevlana"; lat=37.8700; lon=32.5000},
        @{name="Haciveyiszade"; lat=37.8720; lon=32.4950},
        @{name="Saraykoy"; lat=37.8850; lon=32.5200},
        @{name="Melikdede"; lat=37.8680; lon=32.5050},
        @{name="Akabe"; lat=37.8780; lon=32.5150},
        @{name="Sedirler"; lat=37.8800; lon=32.5080},
        @{name="Kalenderhane"; lat=37.8650; lon=32.4980},
        @{name="Aziziye"; lat=37.8730; lon=32.5020},
        @{name="Alavardi"; lat=37.8620; lon=32.5000}
    )
    "Eregli" = @(
        @{name="Zengen"; lat=37.5170; lon=34.0530},
        @{name="Orhaniye"; lat=37.5200; lon=34.0450},
        @{name="Hamidiye"; lat=37.5150; lon=34.0400},
        @{name="Akhuyuk"; lat=37.5100; lon=34.0600},
        @{name="Beykoy"; lat=37.5250; lon=34.0350}
    )
    "Aksehir" = @(
        @{name="Altintas"; lat=38.3600; lon=31.4150},
        @{name="Engilli"; lat=38.3550; lon=31.4100},
        @{name="Sorkun"; lat=38.3650; lon=31.4200},
        @{name="Sultandagi"; lat=38.3500; lon=31.4050},
        @{name="Yarenler"; lat=38.3700; lon=31.4250}
    )
    "Beysehir" = @(
        @{name="Yeni Mahalle"; lat=37.6750; lon=31.7250},
        @{name="Esrefoglu"; lat=37.6700; lon=31.7200},
        @{name="Golyaka"; lat=37.6650; lon=31.7150},
        @{name="Bayindi"; lat=37.6900; lon=31.7350},
        @{name="Huseyin Celepi"; lat=37.6800; lon=31.7300}
    )
    "Cihanbeyli" = @(
        @{name="Yeni Mahalle"; lat=38.6550; lon=32.9250},
        @{name="Gokpinar"; lat=38.6500; lon=32.9200},
        @{name="Karkin"; lat=38.6600; lon=32.9300},
        @{name="Golbasi"; lat=38.6650; lon=32.9350}
    )
    "Cumra" = @(
        @{name="Alibeyhuyugu"; lat=37.5750; lon=32.7750},
        @{name="Iceri Cumra"; lat=37.5700; lon=32.7700},
        @{name="Okcu"; lat=37.5800; lon=32.7800},
        @{name="Inli"; lat=37.5650; lon=32.7650},
        @{name="Karkin"; lat=37.5850; lon=32.7850}
    )
    "Derbent" = @(
        @{name="Yenicekoy"; lat=37.4050; lon=32.0200},
        @{name="Gokce"; lat=37.4100; lon=32.0250},
        @{name="Pinarli"; lat=37.4000; lon=32.0150}
    )
    "Derebucak" = @(
        @{name="Yeni Mahalle"; lat=37.3550; lon=31.5150},
        @{name="Pinarkaya"; lat=37.3500; lon=31.5100},
        @{name="Cukurkoy"; lat=37.3600; lon=31.5200}
    )
    "Doganhisar" = @(
        @{name="Yeni Mahalle"; lat=38.1450; lon=31.6750},
        @{name="Ilgin"; lat=38.1500; lon=31.6800},
        @{name="Karaali"; lat=38.1400; lon=31.6700}
    )
    "Emirgazi" = @(
        @{name="Kayaonu"; lat=37.9050; lon=33.8350},
        @{name="Yaylacik"; lat=37.9100; lon=33.8400},
        @{name="Kavak"; lat=37.9000; lon=33.8300}
    )
    "Guneysinir" = @(
        @{name="Merkez"; lat=37.2750; lon=32.7250},
        @{name="Boyali"; lat=37.2800; lon=32.7300},
        @{name="Gumeli"; lat=37.2700; lon=32.7200}
    )
    "Hadim" = @(
        @{name="Yeni Mahalle"; lat=36.9850; lon=32.4550},
        @{name="Bolat"; lat=36.9900; lon=32.4600},
        @{name="Taskent"; lat=36.9800; lon=32.4500}
    )
    "Halkapinar" = @(
        @{name="Merkez"; lat=37.4550; lon=34.1650},
        @{name="Yenikoy"; lat=37.4600; lon=34.1700}
    )
    "Huyuk" = @(
        @{name="Yeni Mahalle"; lat=37.9550; lon=31.6050},
        @{name="Seki"; lat=37.9600; lon=31.6100},
        @{name="Karacaoren"; lat=37.9500; lon=31.6000}
    )
    "Ilgin" = @(
        @{name="Istiklal"; lat=38.2800; lon=31.9150},
        @{name="Argithanli"; lat=38.2850; lon=31.9200},
        @{name="Cav"; lat=38.2750; lon=31.9100},
        @{name="Merkez"; lat=38.2900; lon=31.9250}
    )
    "Kadinhani" = @(
        @{name="Yeni Mahalle"; lat=38.2350; lon=32.2150},
        @{name="Atlanti"; lat=38.2400; lon=32.2200},
        @{name="Korualan"; lat=38.2300; lon=32.2100}
    )
    "Karapinar" = @(
        @{name="Yeni Mahalle"; lat=37.7150; lon=33.5550},
        @{name="Yesilova"; lat=37.7200; lon=33.5600},
        @{name="Akoren"; lat=37.7100; lon=33.5500},
        @{name="Hotamis"; lat=37.7250; lon=33.5650}
    )
    "Kulu" = @(
        @{name="Yeni Mahalle"; lat=39.0850; lon=33.0750},
        @{name="Merkez"; lat=39.0900; lon=33.0800},
        @{name="Kozanli"; lat=39.0800; lon=33.0700},
        @{name="Tavsancali"; lat=39.0950; lon=33.0850}
    )
    "Sarayonu" = @(
        @{name="Yeni Mahalle"; lat=38.2650; lon=32.4050},
        @{name="Gozlu"; lat=38.2700; lon=32.4100},
        @{name="Ladik"; lat=38.2600; lon=32.4000}
    )
    "Seydisehir" = @(
        @{name="Alaylar"; lat=37.4250; lon=31.8450},
        @{name="Cumhuriyet"; lat=37.4300; lon=31.8500},
        @{name="Gevrekli"; lat=37.4200; lon=31.8400},
        @{name="Ortakoy"; lat=37.4350; lon=31.8550}
    )
    "Taskent" = @(
        @{name="Merkez"; lat=36.9150; lon=32.4950},
        @{name="Balcilar"; lat=36.9200; lon=32.5000},
        @{name="Afsar"; lat=36.9100; lon=32.4900}
    )
    "Tuzlukcu" = @(
        @{name="Yeni Mahalle"; lat=38.4850; lon=31.5550},
        @{name="Ortakoy"; lat=38.4900; lon=31.5600}
    )
    "Yalihuyuk" = @(
        @{name="Yeni Mahalle"; lat=37.2950; lon=31.5450},
        @{name="Gokce"; lat=37.3000; lon=31.5500}
    )
    "Yunak" = @(
        @{name="Yeni Mahalle"; lat=38.8150; lon=31.7350},
        @{name="Harmandali"; lat=38.8200; lon=31.7400},
        @{name="Turgut"; lat=38.8100; lon=31.7300}
    )
    "Ahirli" = @(
        @{name="Merkez"; lat=37.1350; lon=32.0250},
        @{name="Gokkuyu"; lat=37.1400; lon=32.0300}
    )
    "Akoren" = @(
        @{name="Merkez"; lat=37.4650; lon=32.3850},
        @{name="Kiziloren"; lat=37.4700; lon=32.3900}
    )
    "Altinekin" = @(
        @{name="Merkez"; lat=38.3050; lon=32.8650},
        @{name="Obruk"; lat=38.3100; lon=32.8700}
    )
    "Bozkir" = @(
        @{name="Merkez"; lat=37.1900; lon=32.2450},
        @{name="Uzunkaya"; lat=37.1950; lon=32.2500},
        @{name="Hisarlik"; lat=37.1850; lon=32.2400}
    )
    "Catalhuyuk" = @(
        @{name="Merkez"; lat=37.6700; lon=32.8280}
    )
}

# Create Neighbourhoods
Write-Host ""
Write-Host "Creating Neighbourhoods..." -ForegroundColor Yellow

foreach ($districtName in $neighbourhoodsData.Keys) {
    $districtId = $districtIds[$districtName]
    if ($districtId) {
        Write-Host "  District: $districtName (ID: $districtId)" -ForegroundColor Cyan
        foreach ($n in $neighbourhoodsData[$districtName]) {
            try {
                $body = "{`"neighbourhoodName`": `"$($n.name)`", `"lat`": $($n.lat), `"lon`": $($n.lon)}"
                $response = Invoke-RestMethod -Uri "$baseUrl/$districtId/neighbourhoods" -Method POST -ContentType "application/json" -Body $body
                Write-Host "    + $($n.name)" -ForegroundColor Green
            } catch {
                Write-Host "    - $($n.name) (skipped)" -ForegroundColor Yellow
            }
        }
    }
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Magenta
Write-Host "Data Insertion Complete!" -ForegroundColor Magenta
Write-Host "========================================" -ForegroundColor Magenta
Write-Host ""
Write-Host "Summary:" -ForegroundColor White
Write-Host "  Districts: 31" -ForegroundColor Cyan
Write-Host "  Neighbourhoods: 119" -ForegroundColor Cyan

