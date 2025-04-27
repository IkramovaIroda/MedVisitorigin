$(document).ready(function() {
    // Полный перевод на узбекский и русский
    function updateLanguage(lang) {
        $('[data-ru]').each(function() {
            let text = lang === 'ru' ? $(this).data('ru') : $(this).data('uz');
            if ($(this).is('h1, h2, h3, p, span, button, a')) {
                $(this).text(text);
            }
        });
        $('.lang-link').removeClass('active');
        $(`.lang-link[data-lang="${lang}"]`).addClass('active');
    }

    // Инициализация языка по умолчанию
    updateLanguage('ru');

    $('.lang-link').click(function(e) {
        e.preventDefault();
        let lang = $(this).data('lang');
        updateLanguage(lang);
    });

    // Плавная прокрутка
    $('nav a').click(function(e) {
        if ($(this).attr('href').startsWith('#')) {
            e.preventDefault();
            let target = $(this).attr('href');
            $('html, body').animate({
                scrollTop: $(target).offset().top - 70
            }, 800);
        }
    });

    // Модальное окно для врачей
    const doctorInfo = {
        1: { 
            name_ru: "Иванов С.П.", 
            name_uz: "Ivanov S.P.", 
            specialty_ru: "Терапевт", 
            specialty_uz: "Terapevt", 
            experience_ru: "Стаж: 15 лет", 
            experience_uz: "Tajriba: 15 yil",
            clinic_ru: "Клиника: Здоровье", 
            clinic_uz: "Klinika: Sog'liq",
            info_ru: "Опыт работы 15 лет. Специалист по общим заболеваниям. Проводит диагностику и лечение хронических заболеваний.", 
            info_uz: "15 yillik tajriba. Umumiy kasalliklar bo'yicha mutaxassis. Surunkali kasalliklarni tashxislash va davolashni amalga oshiradi."
        },
        2: { 
            name_ru: "Петрова А.И.", 
            name_uz: "Petrova A.I.", 
            specialty_ru: "Кардиолог", 
            specialty_uz: "Kardiolog", 
            experience_ru: "Стаж: 12 лет", 
            experience_uz: "Tajriba: 12 yil",
            clinic_ru: "Клиника: Сердце", 
            clinic_uz: "Klinika: Yurak",
            info_ru: "Специалист по сердечно-сосудистым заболеваниям. Проводит ЭКГ и УЗИ сердца.", 
            info_uz: "Yurak-qon tomir kasalliklari bo'yicha mutaxassis. EKG va yurak UZI o'tkazadi."
        },
        3: { 
            name_ru: "Сидоров А.В.", 
            name_uz: "Sidorov A.V.", 
            specialty_ru: "Невролог", 
            specialty_uz: "Nevrolog", 
            experience_ru: "Стаж: 10 лет", 
            experience_uz: "Tajriba: 10 yil",
            clinic_ru: "Клиника: Нейрон", 
            clinic_uz: "Klinika: Neyron",
            info_ru: "Лечение неврологических расстройств. Специализируется на мигренях и эпилепсии.", 
            info_uz: "Nevrologik kasalliklarni davolash. Migren va epilepsiya bo'yicha ixtisoslashgan."
        },
        4: { 
            name_ru: "Кузнецова О.С.", 
            name_uz: "Kuznetsova O.S.", 
            specialty_ru: "Педиатр", 
            specialty_uz: "Pediatr", 
            experience_ru: "Стаж: 8 лет", 
            experience_uz: "Tajriba: 8 yil",
            clinic_ru: "Клиника: Малыш", 
            clinic_uz: "Klinika: Bolajon",
            info_ru: "Забота о здоровье детей. Проводит профилактические осмотры и вакцинацию.", 
            info_uz: "Bolalar salomatligi haqida qayg'urish. Profilaktik ko'riklar va emlashni amalga oshiradi."
        }
    };

    $('.details-btn').click(function() {
        let doctorId = $(this).data('doctor');
        let lang = $('.lang-link.active')?.data('lang') || 'ru';
        let doctor = doctorInfo[doctorId];
        $('#modal-doctor-name').text(lang === 'ru' ? doctor.name_ru : doctor.name_uz);
        $('#modal-doctor-specialty').text(lang === 'ru' ? doctor.specialty_ru : doctor.specialty_uz);
        $('#modal-doctor-experience').text(lang === 'ru' ? doctor.experience_ru : doctor.experience_uz);
        $('#modal-doctor-clinic').text(lang === 'ru' ? doctor.clinic_ru : doctor.clinic_uz);
        $('#modal-doctor-info').text(lang === 'ru' ? doctor.info_ru : doctor.info_uz);
        $('#doctor-modal').fadeIn().addClass('animate__animated animate__zoomIn');
    });

    $('.close-btn').click(function() {
        $('#doctor-modal').fadeOut().removeClass('animate__animated animate__zoomIn');
    });

    // Анимация кнопки записи
    $('.btn').click(function() {
        let originalText = $(this).text();
        $(this).animate({ width: '200px' }, 300).text($(this).data('ru') === 'Записаться' ? 'Заявка отправлена!' : 'Ariza yuborildi!');
        setTimeout(() => {
            $(this).animate({ width: '150px' }, 300).text(originalText);
        }, 1500);
    });

    // Изменение цвета header при прокрутке
    $(window).scroll(function() {
        if ($(this).scrollTop() > 50) {
            $('header').css({
                'background-color': '#f0f7f4',
                'box-shadow': '0 4px 15px rgba(0,0,0,0.1)'
            });
        } else {
            $('header').css({
                'background-color': '#ffffff',
                'box-shadow': '0 2px 10px rgba(0,0,0,0.2)'
            });
        }
    });

    // Анимация появления элементов при прокрутке
    $(window).scroll(function() {
        $('.doctor-card, .service-item, .testimonial-box').each(function() {
            let topOfElement = $(this).offset().top;
            let bottomOfWindow = $(window).scrollTop() + $(window).height();
            if (bottomOfWindow > topOfElement) {
                $(this).addClass('animate__animated animate__fadeInUp');
            }
        });
    });

    // Дополнительная динамика
    // 1. Плавное появление hero-section
    $('.hero-section').addClass('animate__animated animate__fadeIn').css('animation-duration', '1.5s');

    // 2. Анимация при наведении на карточки услуг
    $('.service-item').hover(
        function() {
            $(this).addClass('animate__animated animate__pulse');
        },
        function() {
            $(this).removeClass('animate__animated animate__pulse');
        }
    );

    // 3. Автоматическая прокрутка карусели отзывов
    function autoScrollTestimonials() {
        $('.testimonials-carousel').animate({ scrollLeft: '+=300' }, 1000, function() {
            if ($(this).scrollLeft() + $(this).innerWidth() >= $(this)[0].scrollWidth) {
                $(this).animate({ scrollLeft: 0 }, 1000);
            }
        });
    }
    setInterval(autoScrollTestimonials, 5000);

    // 4. Плавное появление модального окна
    $('#doctor-modal').on('show', function() {
        $(this).addClass('animate__animated animate__zoomIn');
    });

    // 5. Анимация логотипа при загрузке
    $('.logo img').addClass('animate__animated animate__bounceIn').css('animation-duration', '1s');

    // 6. Подсветка активного пункта меню
    $(window).on('scroll', function() {
        $('nav a').each(function() {
            let section = $($(this).attr('href'));
            if (section.length && $(window).scrollTop() >= section.offset().top - 80) {
                $('nav a').removeClass('active');
                $(this).addClass('active');
            }
        });
    });
});