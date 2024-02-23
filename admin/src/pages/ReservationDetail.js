import classNames from 'classnames'
import BtnDark from '../components/button/BtnDark'
import BtnLight from '../components/button/BtnLight'
import AdminPage from '../components/common/AdminPage'
import { useParams, useNavigate } from 'react-router-dom'
import { useEffect, useRef, useState } from 'react'
import AddImageModal from '../components/modal/AddImageModal'
import {
  deleteMaintenanceImage,
  deleteProgress,
  getReservationDetail,
  postProgress
} from '../api/ReservationApi'
import BtnGroup from '../components/button/BtnGroup'
import InfoTable from '../components/table/InfoTable'
import InfoRow from '../components/table/InfoRow'
import InfoItem from '../components/table/InfoItem'
import InfoGrid from '../components/table/InfoGrid'
import InfoSteps from '../components/table/InfoSteps'
import serviceTypes from '../constants/serviceTypes'
import ReservationSteps from '../components/common/ReservationSteps'
import InfoImage from '../components/table/InfoImage'

export default function ReservationDetail () {
  const navigate = useNavigate()
  const { reservationId } = useParams()

  const [isAdding, setIsAdding] = useState(false)
  const [modalVisible, setModalVisible] = useState(false)
  const [modalStatus, setModalStatus] = useState(0)

  const [shopName, setShopName] = useState(null)
  const [customerName, setCustomerName] = useState(null)
  const [carInfo, setCarInfo] = useState(null)
  const [departureDate, setDepartureDate] = useState(null)
  const [pickupDate, setPickupDate] = useState(null)
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
    filter: false
  })
  const [customerRequest, setCustomerRequest] = useState(null)
  const [coupon, setCoupon] = useState(null)
  const [reservationSteps, setReservationSteps] = useState([])
  const [beforeImages, setBeforeImages] = useState([])
  const [afterImages, setAfterImages] = useState([])

  const stepTitleRef = useRef()
  const stepContentRef = useRef()

  const [selectedStep, setSelectedStep] = useState('예약완료')
  const [stepContent, setStepContent] = useState(null)

  useEffect(() => {
    getReservationDetail(reservationId)
      .then(response => {
        setShopName(response.repairShop)
        setCustomerName(response.customerName)
        setCarInfo(`${response.carSellName} (${response.carPlateNumber})`)
        setDepartureDate(response.from)
        setPickupDate(response.to)
        setServices(response.serviceType)
        setCustomerRequest(response.customerRequest)
        setCoupon(response.couponSerialNumber)
        setReservationSteps(response.progressStage)
        setBeforeImages(response.beforeImages)
        setAfterImages(response.afterImages)
      })
      .catch(error => {
        console.error('Error registering car:', error)
      })
  }, [reservationId])

  function deleteStep (stepId) {
    deleteProgress(stepId).then(() => {
      setReservationSteps(reservationSteps.filter(item => item.id !== stepId))
    })
  }

  function addStep () {
    setSelectedStep('예약완료')
    setIsAdding(true)
  }

  function onChangeStepTitle (event) {
    setSelectedStep(event.target.value)
  }

  function onChangeStepContent (event) {
    setStepContent(event.target.value)
  }

  function submitStep () {
    postProgress(reservationId, selectedStep, stepContent).then(response => {
      setReservationSteps([
        ...reservationSteps,
        {
          id: response.stepId,
          step: selectedStep,
          detail: stepContent
        }
      ])
    })
    setIsAdding(false)
  }

  function cancelStep () {
    setIsAdding(false)
  }

  function deleteImage (imageId) {
    deleteMaintenanceImage(imageId).then(() => {
      setBeforeImages(beforeImages.filter(item => item.id !== imageId))
      setAfterImages(afterImages.filter(item => item.id !== imageId))
    })
  }

  function showImageModal (status) {
    setModalVisible(true)
    setModalStatus(status)
  }

  function showAddedImage (imageId, status, imageUrl) {
    if (status === 0) {
      setBeforeImages([...beforeImages, { id: imageId, url: imageUrl }])
    } else {
      setAfterImages([...afterImages, { id: imageId, url: imageUrl }])
    }
  }

  function closeModal () {
    setModalVisible(false)
  }

  return (
    <>
      <AdminPage pageName='예약관리'>
        <InfoTable>
          <InfoRow>
            <InfoItem label='No'>
              <span>{reservationId}</span>
            </InfoItem>
          </InfoRow>
          <InfoRow>
            <InfoItem label='정비소'>
              <span>{shopName}</span>
            </InfoItem>
          </InfoRow>
          <InfoRow>
            <InfoItem label='고객명'>
              <span>{customerName}</span>
            </InfoItem>
            <InfoItem label='차량 정보'>
              <span>{carInfo}</span>
            </InfoItem>
          </InfoRow>
          <InfoRow>
            <InfoItem label='센터 방문'>
              <span>{departureDate}</span>
            </InfoItem>
            <InfoItem label='픽업'>
              <span>{pickupDate}</span>
            </InfoItem>
          </InfoRow>
          <InfoRow>
            <InfoGrid label='서비스'>
              {Object.keys(services).map((key, index) => {
                if (!services[key]) return null
                return (
                  <span key={index} className={classNames('service')}>
                    {serviceTypes[key]}
                  </span>
                )
              })}
            </InfoGrid>
          </InfoRow>
          <InfoRow>
            <InfoItem label='고객 요청'>
              <span>{customerRequest}</span>
            </InfoItem>
          </InfoRow>
          <InfoRow>
            <InfoItem label='쿠폰 번호'>
              <span>{coupon}</span>
            </InfoItem>
          </InfoRow>
          <InfoRow>
            <InfoSteps label='진행 단계'>
              {reservationSteps.map(step => (
                <div key={step.id} className={classNames('step')}>
                  <span className={classNames('step-title')}>{step.step}</span>
                  <span className={classNames('step-content')}>
                    {step.detail}
                  </span>
                  <BtnLight text={'삭제'} onClick={() => deleteStep(step.id)} />
                </div>
              ))}
              {isAdding ? (
                <div className={classNames('step')}>
                  <select
                    className={classNames('input-title')}
                    value={selectedStep}
                    onChange={onChangeStepTitle}
                    ref={stepTitleRef}
                  >
                    <ReservationSteps />
                  </select>
                  <input
                    className={classNames('input-content')}
                    onChange={onChangeStepContent}
                    ref={stepContentRef}
                  ></input>
                  <BtnLight text={'적용'} onClick={submitStep} />
                  <BtnLight text={'취소'} onClick={cancelStep} />
                </div>
              ) : (
                <div className={classNames('step')}>
                  <div className={classNames('btn-add')} onClick={addStep}>
                    <span>추가</span>
                  </div>
                </div>
              )}
            </InfoSteps>
          </InfoRow>
          <InfoRow>
            <InfoItem label='정비 전 사진'>
              {beforeImages.map(image => (
                <InfoImage
                  image={image}
                  onDelete={() => deleteImage(image.id)}
                />
              ))}
              <BtnLight
                text={'추가'}
                onClick={() => {
                  showImageModal(0)
                }}
              />
            </InfoItem>
          </InfoRow>
          <InfoRow>
            <InfoItem label='정비 후 사진'>
              {afterImages.map(image => (
                <InfoImage
                  image={image}
                  onDelete={() => deleteImage(image.id)}
                />
              ))}
              <BtnLight
                text={'추가'}
                onClick={() => {
                  showImageModal(1)
                }}
              />
            </InfoItem>
          </InfoRow>
        </InfoTable>
        <BtnGroup>
          <BtnDark text={'목록'} onClick={() => navigate('/reservation')} />
        </BtnGroup>
      </AdminPage>
      <AddImageModal
        reservationId={reservationId}
        status={modalStatus}
        onPost={showAddedImage}
        onClose={closeModal}
        visible={modalVisible}
      />
    </>
  )
}
