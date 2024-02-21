import classNames from "classnames";
import SideBar from "../components/SideBar";
import { Link } from "react-router-dom";
import { BtnLight } from "../components/BtnLight";
import BtnDark from "../components/BtnDark";

export default function ReservationDetail() {
  const reservationId = 10;
  const shopName = "블루핸즈 인천공항점";
  const customerName = "김희진";
  const carSellName = "Genesis G80";
  const departureDate = "2024-01-01 10:00";
  const pickupDate = "2024-01-02 10:00";
  const services = ["aa", "bb", "cc"];
  const customerRequest = "에어컨 수리 부탁드려요.";
  const coupon = "GENESISAIRPORT1234";
  const reservationSteps = [
    {
      title: "예약 완료",
      content: null,
    },
    {
      title: "차량 인수",
      content: "14시 이후에 정비 예정입니다.",
    },
    {
      title: "정비중",
      content: "에어컨 점검은 못 할듯...",
    },
    {
      title: "보관중",
      content: "인천공항 주차장에 보관 중입니다.",
    },
  ];
  const beforeImages = [
    {
      id: 1,
      imageUrl:
        "https://genesis-airport.s3.ap-northeast-2.amazonaws.com/maintenance/car3.jpg",
    },
    {
      id: 2,
      imageUrl:
        "https://genesis-airport.s3.ap-northeast-2.amazonaws.com/maintenance/car3.jpg",
    },
    {
      id: 3,
      imageUrl:
        "https://genesis-airport.s3.ap-northeast-2.amazonaws.com/maintenance/car3.jpg",
    },
  ];
  const afterImages = [];

  function deleteImage(imageId) {
    console.log("delete " + imageId);
  }

  return (
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
                  <span>차종</span>
                </div>
                <div className={classNames("td")}>
                  <span>{carSellName}</span>
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
                  {services.map((service) => (
                    <span>{service}</span>
                  ))}
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
                    <div key={step.title} className={classNames("step")}>
                      <span className={classNames("step-title")}>
                        {step.title}
                      </span>
                      <span className={classNames("step-content")}>
                        {step.content}
                      </span>
                      <BtnLight text={"삭제"} />
                    </div>
                  ))}
                  <div className={classNames("step")}>
                    <div className={classNames("btn-add")}>
                      <span>추가</span>
                    </div>
                  </div>
                </div>
              </div>
              <div className={classNames("tr")}>
                <div className={classNames("td", "header")}>
                  <span>정비 전 사진</span>
                </div>
                <div className={classNames("td")}>
                  {beforeImages.map((image) => (
                    <div
                      key={image.id}
                      className={classNames("image")}
                      style={{
                        background: `url(${image.imageUrl}) lightgray 50%`,
                      }}
                    >
                      <div
                        className={classNames("btn-delete")}
                        onClick={deleteImage(image.id)}
                      >
                        <span>삭제</span>
                      </div>
                    </div>
                  ))}
                  <BtnLight text={"추가"} />
                </div>
              </div>
              <div className={classNames("tr")}>
                <div className={classNames("td", "header")}>
                  <span>정비 전 사진</span>
                </div>
                <div className={classNames("td")}>
                  {afterImages.map((image) => (
                    <div
                      key={image.id}
                      className={classNames("image")}
                      style={{
                        background: `url(${image.imageUrl}) lightgray 50%`,
                      }}
                    >
                      <div
                        className={classNames("btn-delete")}
                        onClick={deleteImage(image.id)}
                      >
                        <span>삭제</span>
                      </div>
                    </div>
                  ))}
                  <BtnLight text={"추가"} />
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
  );
}
