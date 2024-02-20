import classNames from "classnames";
import SideBar from "../components/SideBar";
import BtnDark, { BtnLight } from "../components/Button";
import { Link, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import AddImageModal from "../components/modal/AddImageModal";
import { getReservationDetail } from "../api/ReservationApi";

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
    const [services, setServices] = useState([]);
    const [customerRequest, setCustomerRequest] = useState(null);
    const [coupon, setCoupon] = useState(null);
    const [reservationSteps, setReservationSteps] = useState([]);
    const [beforeImages, setBeforeImages] = useState([]);
    const [afterImages, setAfterImages] = useState([]);

    useEffect(() => {
        getReservationDetail(reservationId)
        .then(response => {
            setShopName(response.repairShop);
            setCustomerName(response.customerId);
            setCarInfo(`${response.carSellName} (${response.carPlateNumber})`);
            setDepartureDate(response.from);
            setPickupDate(response.to);
            setServices([]);
            // TODO: service들 넣기
            setCustomerRequest(response.customerRequest);
            setCoupon(response.couponSerialNumber);
            setReservationSteps(response.progressStage);
            setBeforeImages(response.beforeImages);
            setAfterImages(response.afterImages);
        })
        .catch(error => {
          console.error('Error registering car:', error);
        });
    }, [reservationId])

    function deleteStep(stepName) {
        console.log('delete ' + stepName);
    }

    function addStep() {
        setIsAdding(true);
    }

    function submitStep() {
        setIsAdding(false);
    }

    function cancelStep() {
        setIsAdding(false);
    }

    function deleteImage(imageId) {
        console.log('delete ' + imageId);
    }

    function showImageModal(status) {
        setModalVisible(true);
        setModalStatus(status);
    }

    function closeModal() {
        setModalVisible(false);
    }

    return (
    <>
      <div className={classNames('page')}>
        <SideBar currentPage={'reservation'} />
        <div className={classNames('body')}>
            <div className={classNames('title')}>
                <span>예약 관리</span>
            </div>
            <div className={classNames('content')}>
                <div className={classNames('info-table')}>
                    <div className={classNames('tbody')}>
                        <div className={classNames('tr')}>
                            <div className={classNames('td', 'header')}>
                                <span>No</span>
                            </div>
                            <div className={classNames('td')}>
                                <span>{reservationId}</span>
                            </div>
                        </div>
                        <div className={classNames('tr')}>
                            <div className={classNames('td', 'header')}>
                                <span>정비소</span>
                            </div>
                            <div className={classNames('td')}>
                                <span>{shopName}</span>
                            </div>
                        </div>
                        <div className={classNames('tr')}>
                            <div className={classNames('td', 'header')}>
                                <span>고객명</span>
                            </div>
                            <div className={classNames('td')}>
                                <span>{customerName}</span>
                            </div>
                            <div className={classNames('td', 'header')}>
                                <span>차량 정보</span>
                            </div>
                            <div className={classNames('td')}>
                                <span>{carInfo}</span>
                            </div>
                        </div>
                        <div className={classNames('tr')}>
                            <div className={classNames('td', 'header')}>
                                <span>센터 방문</span>
                            </div>
                            <div className={classNames('td')}>
                                <span>{departureDate}</span>
                            </div>
                            <div className={classNames('td', 'header')}>
                                <span>픽업</span>
                            </div>
                            <div className={classNames('td')}>
                                <span>{pickupDate}</span>
                            </div>
                        </div>
                        <div className={classNames('tr')}>
                            <div className={classNames('td', 'header')}>
                                <span>선택 서비스</span>
                            </div>
                            <div className={classNames('td', 'grid')}>
                                {services.map((service) => (
                                    <span key={service}>{service}</span>
                                ))}
                            </div>
                        </div>
                        <div className={classNames('tr')}>
                            <div className={classNames('td', 'header')}>
                                <span>고객 요청</span>
                            </div>
                            <div className={classNames('td')}>
                                <span>{customerRequest}</span>
                            </div>
                        </div>
                        <div className={classNames('tr')}>
                            <div className={classNames('td', 'header')}>
                                <span>쿠폰 번호</span>
                            </div>
                            <div className={classNames('td')}>
                                <span>{coupon}</span>
                            </div>
                        </div>
                        <div className={classNames('tr')}>
                            <div className={classNames('td', 'header')}>
                                <span>진행 단계</span>
                            </div>
                            <div className={classNames('td', 'reservation-steps')}>
                                {reservationSteps.map((step) => (
                                    <div key={step.name} className={classNames('step')}>
                                        <span className={classNames('step-title')}>{step.name}</span>
                                        <span className={classNames('step-content')}>{step.detail}</span>
                                        <BtnLight text={'삭제'} onClick={() => deleteStep(step.name)}/>
                                    </div>
                                ))}
                                {isAdding && <div className={classNames('step')}>
                                    <input className={classNames('input-title')}></input>
                                    <input className={classNames('input-content')}></input>
                                    <BtnLight text={'적용'} onClick={submitStep}/>
                                    <BtnLight text={'취소'} onClick={cancelStep}/>
                                </div>}
                                {!isAdding && <div className={classNames('step')}>
                                    <div className={classNames('btn-add')} onClick={addStep}>
                                        <span>추가</span>
                                    </div>
                                </div>}
                            </div>
                        </div>
                        <div className={classNames('tr')}>
                            <div className={classNames('td', 'header')}>
                                <span>정비 전 사진</span>
                            </div>
                            <div className={classNames('td')}>
                                {beforeImages.map((image) => (
                                    <div key={image.id} className={classNames('image')} style={{background: `url(${image.imageUrl}) lightgray 50%`}}>
                                        <div className={classNames('btn-delete')} onClick={() => deleteImage(image.id)}>
                                            <span>삭제</span>
                                        </div>
                                    </div>
                                ))}
                                <BtnLight text={'추가'} onClick={() => {showImageModal(0)}}/>
                            </div>
                        </div>
                        <div className={classNames('tr')}>
                            <div className={classNames('td', 'header')}>
                                <span>정비 전 사진</span>
                            </div>
                            <div className={classNames('td')}>
                                {afterImages.map((image) => (
                                    <div key={image.id} className={classNames('image')} style={{background: `url(${image.imageUrl}) lightgray 50%`}}>
                                        <div className={classNames('btn-delete')} onClick={deleteImage(image.id)}>
                                            <span>삭제</span>
                                        </div>
                                    </div>
                                ))}
                                <BtnLight text={'추가'} onClick={() => {showImageModal(1)}}/>
                            </div>
                        </div>
                    </div>
                </div>
                <div className={classNames('btn-bottom')}>
                    <Link to='/reservation'>
                        <BtnDark text={'목록'}/>
                    </Link>
                </div>
            </div>
        </div>
      </div>
        <AddImageModal status={modalStatus} onClose={closeModal} visible={modalVisible} />
    </>
    );
  }
  