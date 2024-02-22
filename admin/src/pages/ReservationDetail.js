import classNames from "classnames";
import SideBar from "../components/SideBar";
import { BtnDark } from "../components/BtnDark";
import { BtnLight } from "../components/BtnLight";
import { Link, useParams } from "react-router-dom";
import { useEffect, useRef, useState } from "react";
import AddImageModal from "../components/modal/AddImageModal";
import {
  deleteMaintenanceImage,
  deleteProgress,
  getReservationDetail,
  postProgress,
} from "../api/ReservationApi";

export default function ReservationDetail() {
  const { reservationId } = useParams();

  const [isAdding, setIsAdding] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);
  const [modalStatus, setModalStatus] = useState(0);

  const [shopName, setShopName] = useState(null);
  const [customerName, setCustomerName] = useState(null);
  const [carInfo, setCarInfo] = useState(null);
  const [departureDate, setDepartureDate] = useState(null);
  const [pickupDate, setPickupDate] = useState(null);
  const [services, setServices] = useState({
    oil: false,
    battery: false,
    engineRun: false,
    engineCooler: false,
    airCooler: false,
    bottom: false,
    breakpad: false,
    lamp: false,
    engineMount: false,
    suspension: false,
    shaft: false,
    scanner: false,
    heater: false,
    tire: false,
    filter: false,
  });
  const [customerRequest, setCustomerRequest] = useState(null);
  const [coupon, setCoupon] = useState(null);
  const [reservationSteps, setReservationSteps] = useState([]);
  const [beforeImages, setBeforeImages] = useState([]);
  const [afterImages, setAfterImages] = useState([]);

  const stepTitleRef = useRef();
  const stepContentRef = useRef();

  const [selectedStep, setSelectedStep] = useState("예약완료");
  const [stepContent, setStepContent] = useState(null);

  useEffect(() => {
    getReservationDetail(reservationId)
      .then((response) => {
        setShopName(response.repairShop);
        setCustomerName(response.customerId);
        setCarInfo(`${response.carSellName} (${response.carPlateNumber})`);
        setDepartureDate(response.from);
        setPickupDate(response.to);
        setServices(response.serviceType);
        setCustomerRequest(response.customerRequest);
        setCoupon(response.couponSerialNumber);
        setReservationSteps(response.progressStage);
        setBeforeImages(response.beforeImages);
        setAfterImages(response.afterImages);
      })
      .catch((error) => {
        console.error("Error registering car:", error);
      });
  }, [reservationId]);

  function deleteStep(stepId) {
    deleteProgress(stepId).then(() => {
      setReservationSteps(
        reservationSteps.filter((item) => item.id !== stepId)
      );
    });
  }

  function addStep() {
    setSelectedStep("예약완료");
    setIsAdding(true);
  }

  function onChangeStepTitle(event) {
    setSelectedStep(event.target.value);
  }

  function onChangeStepContent(event) {
    setStepContent(event.target.value);
  }

  function submitStep() {
    postProgress(reservationId, selectedStep, stepContent).then((response) => {
      setReservationSteps([
        ...reservationSteps,
        {
          id: response.stepId,
          step: selectedStep,
          detail: stepContent,
        },
      ]);
    });
    setIsAdding(false);
  }

  function cancelStep() {
    setIsAdding(false);
  }

  function deleteImage(imageId) {
    deleteMaintenanceImage(imageId).then(() => {
      setBeforeImages(beforeImages.filter((item) => item.id !== imageId));
      setAfterImages(afterImages.filter((item) => item.id !== imageId));
    });
  }

  function showImageModal(status) {
    setModalVisible(true);
    setModalStatus(status);
  }

  function showAddedImage(imageId, status, imageUrl) {
    if (status === 0) {
      setBeforeImages([...beforeImages, { id: imageId, url: imageUrl }]);
    } else {
      setAfterImages([...afterImages, { id: imageId, url: imageUrl }]);
    }
  }

  function closeModal() {
    setModalVisible(false);
  }

  return (
    <>
      <div className={classNames("page")}>
        <SideBar currentPage={"reservation"} />
        <div className={classNames("body")}>
          <div className={classNames("title")}>
            <span>예약 관리</span>
          </div>
          <div className={classNames("content")}>
            <div className={classNames("info-table")}>
              <div className={classNames("tbody")}>
                <div className={classNames("tr")}>
                  <div className={classNames("td", "header")}>
                    <span>No</span>
                  </div>
                  <div className={classNames("td")}>
                    <span>{reservationId}</span>
                  </div>
                </div>
                <div className={classNames("tr")}>
                  <div className={classNames("td", "header")}>
                    <span>정비소</span>
                  </div>
                  <div className={classNames("td")}>
                    <span>{shopName}</span>
                  </div>
                </div>
                <div className={classNames("tr")}>
                  <div className={classNames("td", "header")}>
                    <span>고객명</span>
                  </div>
                  <div className={classNames("td")}>
                    <span>{customerName}</span>
                  </div>
                  <div className={classNames("td", "header")}>
                    <span>차량 정보</span>
                  </div>
                  <div className={classNames("td")}>
                    <span>{carInfo}</span>
                  </div>
                </div>
                <div className={classNames("tr")}>
                  <div className={classNames("td", "header")}>
                    <span>센터 방문</span>
                  </div>
                  <div className={classNames("td")}>
                    <span>{departureDate}</span>
                  </div>
                  <div className={classNames("td", "header")}>
                    <span>픽업</span>
                  </div>
                  <div className={classNames("td")}>
                    <span>{pickupDate}</span>
                  </div>
                </div>
                <div className={classNames("tr")}>
                  <div className={classNames("td", "header")}>
                    <span>선택 서비스</span>
                  </div>
                  <div className={classNames("td", "grid")}>
                    {services.oil && <span>각종 오일 및 호스 상태</span>}
                    {services.battery && <span>배터리 상태 및 충전 전압</span>}
                    {services.engineRun && <span>엔진 구동 상태</span>}
                    {services.engineCooler && <span>엔진 냉각수</span>}
                    {services.airCooler && <span>에어클리너 점검</span>}
                    {services.bottom && <span>하체충격 및 손상 여부</span>}
                    {services.breakpad && (
                      <span>브레이크패드 및 라이닝 마모</span>
                    )}
                    {services.lamp && <span>각종 등화 장치 점검</span>}
                    {services.engineMount && <span>엔진 미션 마운트</span>}
                    {services.suspension && <span>서스펜션 뉴계토우 점검</span>}
                    {services.shaft && (
                      <span>스테빌라이저 및 드라이버 샤프트</span>
                    )}
                    {services.scanner && <span>스캐너 고장코드 진단</span>}
                    {services.heater && <span>에어컨 및 히터 작동 생태</span>}
                    {services.tire && <span>타이어 공기압 및 마모도</span>}
                    {services.filter && <span>에어컨 필터</span>}
                  </div>
                </div>
                <div className={classNames("tr")}>
                  <div className={classNames("td", "header")}>
                    <span>고객 요청</span>
                  </div>
                  <div className={classNames("td")}>
                    <span>{customerRequest}</span>
                  </div>
                </div>
                <div className={classNames("tr")}>
                  <div className={classNames("td", "header")}>
                    <span>쿠폰 번호</span>
                  </div>
                  <div className={classNames("td")}>
                    <span>{coupon}</span>
                  </div>
                </div>
                <div className={classNames("tr")}>
                  <div className={classNames("td", "header")}>
                    <span>진행 단계</span>
                  </div>
                  <div className={classNames("td", "reservation-steps")}>
                    {reservationSteps.map((step) => (
                      <div key={step.id} className={classNames("step")}>
                        <span className={classNames("step-title")}>
                          {step.step}
                        </span>
                        <span className={classNames("step-content")}>
                          {step.detail}
                        </span>
                        <BtnLight
                          text={"삭제"}
                          onClick={() => deleteStep(step.id)}
                        />
                      </div>
                    ))}
                    {isAdding && (
                      <div className={classNames("step")}>
                        <select
                          className={classNames("input-title")}
                          value={selectedStep}
                          onChange={onChangeStepTitle}
                          ref={stepTitleRef}
                        >
                          <option value={"예약완료"}>예약완료</option>
                          <option value={"차량인수"}>차량인수</option>
                          <option value={"정비중"}>정비중</option>
                          <option value={"보관중"}>보관중</option>
                          <option value={"차량인계"}>차량인계</option>
                          <option value={"취소됨"}>취소됨</option>
                        </select>
                        <input
                          className={classNames("input-content")}
                          onChange={onChangeStepContent}
                          ref={stepContentRef}
                        ></input>
                        <BtnLight text={"적용"} onClick={submitStep} />
                        <BtnLight text={"취소"} onClick={cancelStep} />
                      </div>
                    )}
                    {!isAdding && (
                      <div className={classNames("step")}>
                        <div
                          className={classNames("btn-add")}
                          onClick={addStep}
                        >
                          <span>추가</span>
                        </div>
                      </div>
                    )}
                  </div>
                </div>
                <div className={classNames("tr")}>
                  <div className={classNames("td", "header")}>
                    <span>정비 전 사진</span>
                  </div>
                  <div className={classNames("td")}>
                    {beforeImages.map((image) => (
                      <div key={image.id} className={classNames("image")}>
                        <img alt={image.id} src={image.url} />
                        <div
                          className={classNames("btn-delete")}
                          onClick={() => deleteImage(image.id)}
                        >
                          <span>삭제</span>
                        </div>
                      </div>
                    ))}
                    <BtnLight
                      text={"추가"}
                      onClick={() => {
                        showImageModal(0);
                      }}
                    />
                  </div>
                </div>
                <div className={classNames("tr")}>
                  <div className={classNames("td", "header")}>
                    <span>정비 전 사진</span>
                  </div>
                  <div className={classNames("td")}>
                    {afterImages.map((image) => (
                      <div key={image.id} className={classNames("image")}>
                        <img alt={image.id} src={image.url} />
                        <div
                          className={classNames("btn-delete")}
                          onClick={() => deleteImage(image.id)}
                        >
                          <span>삭제</span>
                        </div>
                      </div>
                    ))}
                    <BtnLight
                      text={"추가"}
                      onClick={() => {
                        showImageModal(1);
                      }}
                    />
                  </div>
                </div>
              </div>
            </div>
            <div className={classNames("btn-bottom")}>
              <Link to="/reservation">
                <BtnDark text={"목록"} />
              </Link>
            </div>
          </div>
        </div>
      </div>
      <AddImageModal
        reservationId={reservationId}
        status={modalStatus}
        onPost={showAddedImage}
        onClose={closeModal}
        visible={modalVisible}
      />
    </>
  );
}
