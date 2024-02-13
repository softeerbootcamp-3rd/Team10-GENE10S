import classNames from 'classnames';
import Footer from '../components/Footer';
import Header from '../components/Header';
import React from 'react';
import { useState } from 'react';
import AirportBranch from '../components/AirportBranch';

export default function ReservationIntro() {

  const [selectedButtonKimpo, setSelectedButtonKimpo] = useState('serviceKimpo');
  const [selectedButtonIncheon, setSelectedButtonIncheon] = useState('serviceIncheon');

  
  const handleButtonClickKimpo = (categoryName) => {
    setSelectedButtonKimpo(categoryName);
  };

  const handleButtonClickIncheon = (categoryName) => {
    setSelectedButtonIncheon(categoryName);
  };

  const getContentByButton = (selectedButton) => {
    switch (selectedButton) {
      case 'serviceKimpo':
        return (
          <>
            - 김포공항 무상 발레 서비스
            <br/>
            - 제네시스 전용 안심 주차
            <br/>
            - 소모품 교환
            <br/>
              (엔진오일세트, 에어컨 필터, 와이퍼 블레이드 中 택)
            <br/>
            <br/>
            ※ 안심 주차 비용
            <br/>
            7천 원/일, 6일 째부터 1만 원/일, vat제외 *00시 기준
          </>
        );
      case 'serviceIncheon':
        return (
          <>
            - 공항 셔틀 서비스
            <br/>
            - 기본 점검 15종
            <br/>
            - 차량 클리닝 서비스
            <br/>
            - 제네시스 전용 안심 주차
            <br/>
            <br/>
            ※ 서비스 비용
            <br/>
            7만 원(4박 5일 이내 기준), 6일 째부터 1만 원/일 추가,
            <br/>
            vat제외 *00시 기준
          </>
        );
      case 'targetKimpo':
        return (
          <>
            김포공항 이용 제네시스 고객 중 
            <br/>
            소모품 교환 쿠폰 및 홈투홈 서비스 쿠폰 보유 고객      
          </>
        );
      case 'targetIncheon':
        return (
          <>
            인천공항 이용 제네시스 고객 중 
            <br/>
            소모품 교환 쿠폰 및 홈투홈 서비스 쿠폰 보유 고객      
          </>
        );
      case 'couponCommon':
        return (
          <>
            이용기간 내 소모품 교환 쿠폰 및 홈투홈 서비스 쿠폰 사용
            <br/>
            ※ 서비스 이용 후 쿠폰 차감  
          </>
        );
      case 'usageCommon':
        return (
          <>
            제네시스 홈페이지에서 최대 두 달~최소 2주일 전에 예약
            <br/>
            ※ 이용객이 많은 경우 조기 마감될 수 있습니다.     
          </>
        );
      default:
        return null;
    }
  };
  

  return (
    <>
      <Header />

      <div className={classNames('reserv-intro-page')}>
        <div className={classNames('main-board')}>
          <div className={classNames('image-car')}/>
          <div className={classNames('title-fixed')}>
            <div className={classNames('title-text')}>
              Reservation
            </div>
          </div>
        </div>

        <div className={classNames('button-area')}>
          <div className={classNames('text-title')}>
            <div className={classNames('text')}>
              제네시스가 제공하는
              <br/>
              모빌리티 케어 에어포트 서비스
            </div>
          </div>
          <div className={classNames('main-btn')}>
            <AirportBranch
              name="블루핸즈 김포공항점"
              description="안전과 신뢰를 바탕으로 김포공항에서의 여정을 편안하게 안내합니다."
              imageType="image-1"
            />
            <AirportBranch
              name="블루핸즈 인천공항점"
              description="최상의 편의성과 서비스로 인천공항 여정을 완벽하게 지원합니다."
              imageType="image-2"
            />
          </div>
        </div>

        <div className={classNames('txt-area')}>
          <h3 className={classNames('txt-title')}>
            GENESIS 카 라이프
            <br/>
            에어포트 서비스 소개
          </h3>
          <p className={classNames('txt-info')}>
            제네시스가 지원하는 VIP 카 라이프 프로그램과 함께
            <br/>
            공항에서의 편의와 감성 그리고 가치를 충분히 경험해보세요.
          </p>
        </div>


      <div className={classNames('info-area')}>
        <div className={classNames('service-info')}>
          <h3 className={classNames('title')}>
            에어포트 서비스
          </h3>
          <p className={classNames('content')}>
            여행 혹은 출장을 떠나신 도안 점검 및 정비 등을 제공하고,
            <br/>
            전용 안심 주차장에 차량을 보관해드립니다.
          </p>
        </div>

        <div className={classNames('type-info')}>
          <div className={classNames('image-frame-1')}/>
          <div className={classNames('text-frame')}>
            <h3 className={classNames('txt-title')}>
              김포 에어포트 서비스
            </h3>
            <div className="category">
              <div
                className={`button ${selectedButtonKimpo === 'serviceKimpo' ? 'button-click' : 'button-unclick'}`}
                onClick={() => handleButtonClickKimpo('serviceKimpo')}
              >
                <a className="category-name">서비스 내용</a>
              </div>
              <div
                className={`button ${selectedButtonKimpo === 'targetKimpo' ? 'button-click' : 'button-unclick'}`}
                onClick={() => handleButtonClickKimpo('targetKimpo')}
              >
                <a className="category-name">서비스 대상</a>
              </div>
              <div
                className={`button ${selectedButtonKimpo === 'couponCommon' ? 'button-click' : 'button-unclick'}`}
                onClick={() => handleButtonClickKimpo('couponCommon')}
              >
                <a className="category-name">사용 조건</a>
              </div>
              <div
                className={`button ${selectedButtonKimpo === 'usageCommon' ? 'button-click' : 'button-unclick'}`}
                onClick={() => handleButtonClickKimpo('usageCommon')}
              >
                <a className="category-name">이용 안내</a>
              </div>
            </div>
            <div className={classNames('text-content')}>
              <p className="txt-info">
                {getContentByButton(selectedButtonKimpo)}
              </p>
            </div>
          </div>
        </div>

         <div className={classNames('type-info')}>
          <div className={classNames('text-frame')}>
            <h3 className={classNames('txt-title')}>
              인천 에어포트 서비스
            </h3>
            <div className="category">
              <div
                className={`button ${selectedButtonIncheon === 'serviceIncheon' ? 'button-click' : 'button-unclick'}`}
                onClick={() => handleButtonClickIncheon('serviceIncheon')}
              >
                <a className="category-name">서비스 내용</a>
              </div>
              <div
                className={`button ${selectedButtonIncheon === 'targetIncheon' ? 'button-click' : 'button-unclick'}`}
                onClick={() => handleButtonClickIncheon('targetIncheon')}
              >
                <a className="category-name">서비스 대상</a>
              </div>
              <div
                className={`button ${selectedButtonIncheon === 'couponCommon' ? 'button-click' : 'button-unclick'}`}
                onClick={() => handleButtonClickIncheon('couponCommon')}
              >
                <a className="category-name">사용 조건</a>
              </div>
              <div
                className={`button ${selectedButtonIncheon === 'usageCommon' ? 'button-click' : 'button-unclick'}`}
                onClick={() => handleButtonClickIncheon('usageCommon')}
              >
                <a className="category-name">이용 안내</a>
              </div>
            </div>
            <div className={classNames('text-content')}>
              <p className={classNames('txt-info')}>
              {getContentByButton(selectedButtonIncheon)}
              </p>
            </div>
          </div>
          <div className={classNames('image-frame-2')}/>
        </div>
      </div>

      </div>

      <Footer />
    </>
  );
}