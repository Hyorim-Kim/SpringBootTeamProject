 /*DOMContentLoaded 이벤트를 기다리고, 페이지가 로드되면 다양한 작업을 수행*/
document.addEventListener('DOMContentLoaded', () => {
  "use strict";

  /*Preloader : 페이지가 로딩 중일 때 사용자에게 로딩 중임을 시각적으로 알려주는 기능*/
  /* #preloader라는 ID를 가진 요소를 찾고 해당 요소가 존재하면,
  페이지가 로드될 때 window의 load 이벤트를 기다렸다가 preloader 요소를 제거한다.
  결과적으로 페이지가 완전히 로드되면 preloader가 화면에서 사라지게 된다.*/ 
  const preloader = document.querySelector('#preloader');
  if (preloader) {
    window.addEventListener('load', () => {
      preloader.remove();
    });
  }

  /*모바일 네비게이션 토글(햄버거 메뉴바) : 모바일 네비게이션을 열고 닫기 위한 버튼을 처리한다.
  mobileNavShow와 mobileNavHide 요소를 찾는다.
  .mobile-nav-toggle 클래스를 가진 모든 요소에 대해 클릭 이벤트를 추가하고,
  클릭 시 mobileNavToggle() 함수를 호출하여 네비게이션의 표시 여부를 토글한다.*/
  const mobileNavShow = document.querySelector('.mobile-nav-show');
  const mobileNavHide = document.querySelector('.mobile-nav-hide');

  document.querySelectorAll('.mobile-nav-toggle').forEach(el => {
    el.addEventListener('click', function(event) {
      event.preventDefault();
      mobileNavToogle();
    })
  });

  function mobileNavToogle() {
    document.querySelector('body').classList.toggle('mobile-nav-active');
    mobileNavShow.classList.toggle('d-none');
    mobileNavHide.classList.toggle('d-none');
  }

  /*같은 페이지/해시 링크를 클릭할 때 모바일 네비게이션 숨기기:
  #navbar 내부의 모든 링크에 대해 반복한다.
  만약 링크가 해시(페이지 내부 링크)를 가지고 있다면, 해당 해시로 이동하는 클릭 이벤트에 대한 처리를 추가한다.
  모바일 네비게이션이 활성화되어 있다면, 링크를 클릭하면 mobileNavToggle() 함수를 호출하여 모바일 네비게이션을 닫는다.*/
  document.querySelectorAll('#navbar a').forEach(navbarlink => {

    if (!navbarlink.hash) return;

    let section = document.querySelector(navbarlink.hash);
    if (!section) return;

    navbarlink.addEventListener('click', () => {
      if (document.querySelector('.mobile-nav-active')) {
        mobileNavToogle();
      }
    });

  });

  /*모바일 네비게이션 드롭다운(햄버거 메뉴바 하위메뉴 펼치는거) 토글: 네비게이션 바 내부의 드롭다운 메뉴를 처리
  .navbar .dropdown > a 요소를 찾습니다.
  모바일 네비게이션이 활성화되어 있을 때, 드롭다운 메뉴를 클릭하면 해당 메뉴가 확장되고 축소되며, 아이콘도 변경된다.*/
  const navDropdowns = document.querySelectorAll('.navbar .dropdown > a');

  navDropdowns.forEach(el => {
    el.addEventListener('click', function(event) {
      if (document.querySelector('.mobile-nav-active')) {
        event.preventDefault();
        this.classList.toggle('active');
        this.nextElementSibling.classList.toggle('dropdown-active');

        let dropDownIndicator = this.querySelector('.dropdown-indicator');
        dropDownIndicator.classList.toggle('bi-chevron-up');
        dropDownIndicator.classList.toggle('bi-chevron-down');
      }
    })
  });

  /*스크롤 탑 버튼: 페이지의 상단으로 스크롤하는 버튼을 처리
  스크롤 이벤트를 사용하여 페이지가 일정 위치 이상으로 스크롤되면 버튼이 나타나도록 한다.
  버튼을 클릭하면 페이지가 맨 위로 스크롤된다.*/
  const scrollTop = document.querySelector('.scroll-top');
  if (scrollTop) {
    const togglescrollTop = function() {
      window.scrollY > 100 ? scrollTop.classList.add('active') : scrollTop.classList.remove('active');
    }
    window.addEventListener('load', togglescrollTop);
    document.addEventListener('scroll', togglescrollTop);
    scrollTop.addEventListener('click', window.scrollTo({
      top: 0,
      behavior: 'smooth'
    }));
  }

  /*glightbox 초기화: glightbox 라이브러리를 사용하여 이미지 및 비디오 라이트박스를 초기화
  .glightbox 클래스를 가진 요소들을 라이트박스로 만든다.*/
  const glightbox = GLightbox({
    selector: '.glightbox'
  });

  /*포트폴리오 필터 및 정렬: 포트폴리오 아이템을 필터링하고 정렬하는 기능을 추가
  Isotope 라이브러리를 사용하여 포트폴리오 항목을 필터링하고 정렬한다.*/
  let portfolionIsotope = document.querySelector('.portfolio-isotope');

  if (portfolionIsotope) {

    let portfolioFilter = portfolionIsotope.getAttribute('data-portfolio-filter') ? portfolionIsotope.getAttribute('data-portfolio-filter') : '*';
    let portfolioLayout = portfolionIsotope.getAttribute('data-portfolio-layout') ? portfolionIsotope.getAttribute('data-portfolio-layout') : 'masonry';
    let portfolioSort = portfolionIsotope.getAttribute('data-portfolio-sort') ? portfolionIsotope.getAttribute('data-portfolio-sort') : 'original-order';

    window.addEventListener('load', () => {
      let portfolioIsotope = new Isotope(document.querySelector('.portfolio-container'), {
        itemSelector: '.portfolio-item',
        layoutMode: portfolioLayout,
        filter: portfolioFilter,
        sortBy: portfolioSort
      });

      let menuFilters = document.querySelectorAll('.portfolio-isotope .portfolio-flters li');
      menuFilters.forEach(function(el) {
        el.addEventListener('click', function() {
          document.querySelector('.portfolio-isotope .portfolio-flters .filter-active').classList.remove('filter-active');
          this.classList.add('filter-active');
          portfolioIsotope.arrange({
            filter: this.getAttribute('data-filter')
          });
          if (typeof aos_init === 'function') {
            aos_init();
          }
        }, false);
      });

    });

  }

  /*숫자 카운터 초기화: 숫자 카운터를 초기화하는 코드 
  웹 페이지에서 특정 횟수나 수치를 애니메이션 효과와 함께 보여주기 위한 것*/
  new Swiper('.slides-1', {
    speed: 600,
    loop: true,
    autoplay: {
      delay: 5000,
      disableOnInteraction: false
    },
    slidesPerView: 'auto',
    pagination: {
      el: '.swiper-pagination',
      type: 'bullets',
      clickable: true
    },
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    }
  });

  /*스크롤 시 애니메이션 초기화:
  AOS.init() 함수를 사용하여 스크롤 시 나타나는 애니메이션을 초기화
  load 이벤트가 발생할 때 초기화됨*/
  new Swiper('.slides-2', {
    speed: 600,
    loop: true,
    autoplay: {
      delay: 5000,
      disableOnInteraction: false
    },
    slidesPerView: 'auto',
    pagination: {
      el: '.swiper-pagination',
      type: 'bullets',
      clickable: true
    },
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },
    breakpoints: {
      320: {
        slidesPerView: 1,
        spaceBetween: 20
      },

      1200: {
        slidesPerView: 2,
        spaceBetween: 20
      }
    }
  });
  
  /*애니메이션 라이브러리 초기화*/
  
  /*Pure Counter 초기화:
  Pure Counter는 숫자 카운터를 웹 페이지에 추가하는 데 사용되는 라이브러리
  숫자 카운터는 웹 페이지에서 숫자를 부드럽게 증가시키거나 감소시키는데 사용된다.*/
  new PureCounter(); /*Pure Counter를 초기화하고 페이지 내에서 숫자 카운터를 사용할 수 있도록 설정*/

  /**
   * Animation on scroll function and init
   */
  function aos_init() {
    AOS.init({
      duration: 800,
      easing: 'slide',
      once: true,
      mirror: false
    });
  }
  window.addEventListener('load', () => {
    aos_init();
  });

});