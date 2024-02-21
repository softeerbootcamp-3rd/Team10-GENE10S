import classNames from 'classnames';
import SideBar from "../components/SideBar";
import BtnDark from '../components/Button';
import CustomCalendar from '../components/CustomCalendar';
import React, { useState } from 'react';
import { getReservationList } from '../api/ReservationApi';

export default function ReservationList() {
    
    const today = new Date();
    const next28Day = new Date(today.getTime() + (28 * 24 * 60 * 60 * 1000));

    const [shopName, setShopName] = useState('');
    const [startPickUpDateTime, setStartPickUpDateTime] = useState('');
    const [endPickUpDateTime, setEndPickUpDateTime] = useState('');
    const [startReturnDateTime, setStartReturnDateTime] = useState('');
    const [endReturnDateTime, setEndReturnDateTime] = useState('');
    const [customerName, setCustomerName] = useState('');
    const [sellName, setSellName] = useState('');
    const [stage, setStage] = useState('');

    const [sortColumn, setSortColumn] = useState('');
    const [sortAscending, setSortAscending] = useState(null); 

    const [pageSize, setPageSize] = useState(20);
    const [pageNumber, setPageNumber] = useState(1);

    const [responseData, setResponseData] = useState([]);
    const [pageInfo, setPageInfo] = useState({});

    const handleShopNameChange = (event) => {
        setShopName(event.target.value);
    };

    const handleStartPickUpDateTimeChange = (date) => {
        setStartPickUpDateTime(date);
    }

    const handleEndPickUpDateTimeChange = (date) => {
        setEndPickUpDateTime(date);
    }

    const handleStartReturnDateTimeChange = (date) => {
        setStartReturnDateTime(date);
    }

    const handleEndReturnDateTimeChange = (date) => {
        setEndReturnDateTime(date);
    }

    const handleCustomerNameChange = (event) => {
        setCustomerName(event.target.value);
    };

    const handleSellNameChange = (event) => {
        setSellName(event.target.value);
    };

    const handleStageChange = (event) => {
        setStage(event.target.value);
    };

    const handleSort = (columnName) => {
        if (sortColumn === columnName) {
          setSortAscending(!sortAscending);
        } else {
          setSortColumn(columnName);
          setSortAscending(true); // true가 내림차순 (desc)
        }
    };

    const renderArrow = (columnName) => {
        if (sortColumn === columnName) {
          return sortAscending ? (
            <svg width="14" height="11" viewBox="0 0 10 10" fill="#000000" xmlns="http://www.w3.org/2000/svg">
              <path d="M5 1L1 9H9L5 1Z" fill="#000000"/>
            </svg>
          ) : (
            <svg width="14" height="11" viewBox="0 0 10 10" fill="#000000" xmlns="http://www.w3.org/2000/svg">
              <path d="M5 9L9 1H1L5 9Z" fill="#000000"/>
            </svg>
          );
        }
        return (
          <svg width="14" height="11" viewBox="0 0 10 10" fill="#999999" xmlns="http://www.w3.org/2000/svg">
            <path d="M5 9L9 1H1L5 9Z" fill="#999999"/>
          </svg>
        );
    };

    function handleSearchReservations() {
        getReservationList(
            shopName, startPickUpDateTime, endPickUpDateTime, startReturnDateTime, endReturnDateTime,
            customerName, sellName, stage, sortColumn, sortAscending, pageSize, pageNumber
        )
        .then( response => {
            setResponseData(response.data.data);
            setPageInfo(response.data.pageInfo);
        });
    }

    return (
      <div className={classNames('page')}>
        <SideBar currentPage={'reservation'} />
        <div className={classNames('body')}>
            <div className={classNames('title')}>
                <div>예약 관리</div>
            </div>
            <div className={classNames('content')}>
                <div className={classNames('search-area')}>
                    <div className={classNames('search-row')}>
                        <div className={classNames('search-item')}>
                            <div>정비소</div>
                            <select value={shopName} onChange={handleShopNameChange}>
                                <option value="">정비소를 선택해주세요.</option>
                                <option value="블루핸즈 인천공항점">블루핸즈 인천공항점</option>
                                <option value="블루핸즈 김포공항점">블루핸즈 김포공항점</option>
                            </select>
                        </div>
                    </div>
                    <div className={classNames('search-row')}>
                        <div className={classNames('search-item')}>
                            <div>센터 방문</div>
                            <CustomCalendar value={startPickUpDateTime} onChange={handleStartPickUpDateTimeChange} />
                            <div>~</div>
                            <CustomCalendar value={endPickUpDateTime} onChange={handleEndPickUpDateTimeChange} />
                        </div>
                        <div className={classNames('search-item')}>
                            <div>픽업</div>
                            <CustomCalendar value={startReturnDateTime} onChange={setStartReturnDateTime} />
                            <div>~</div>
                            <CustomCalendar value={endReturnDateTime} onChange={setEndReturnDateTime} />
                        </div>
                    </div>
                    <div className={classNames('search-row')}>
                        <div className={classNames('search-item')}>
                            <div>고객명</div>
                            <input type="text"
                                    value={customerName} 
                                    onChange={handleCustomerNameChange} />
                        </div>
                        <div className={classNames('search-item')}>
                            <div>차종</div>
                            <input type="text"
                                    value={sellName} 
                                    onChange={handleSellNameChange} />
                        </div>
                        <div className={classNames('search-item')}>
                            <div>진행 단계</div>
                            <select value={shopName} onChange={handleStageChange}>
                                <option value="">진행 단계를 선택해주세요.</option>
                                <option value="예약완료">예약 완료</option>
                                <option value="차량인수">차량 인수</option>
                                <option value="정비중">정비 중</option>
                                <option value="보관중">보관 중</option>
                                <option value="차량인계">차량 인계</option>
                            </select>
                        </div>
                        <BtnDark text={"검색"} onClick={handleSearchReservations}/>
                    </div>
                </div>
                <div className={classNames('table')}>
                    <div className={classNames('th')}>
                        <div className={classNames('td')} onClick={() => handleSort('no')}>
                            <div>No {renderArrow('no')}</div>
                        </div>
                        <div className={classNames('td')} >
                            <div>정비소 </div>
                        </div>
                        <div className={classNames('td')} >
                            <div>고객명 </div>
                        </div>
                        <div className={classNames('td')} >
                            <div>차종 </div>
                        </div>
                        <div className={classNames('td')} onClick={() => handleSort('pickUpDateTime')}>
                            <div>센터 방문 {renderArrow('pickUpDateTime')}</div>
                        </div>
                        <div className={classNames('td')} onClick={() => handleSort('returnDateTime')}>
                            <div>픽업 {renderArrow('returnDateTime')}</div>
                        </div>
                        <div className={classNames('td')} >
                            <div>현재 단계 </div>
                        </div>
                    </div>
                    {responseData && responseData.map(item => (
                        <div key={item.reservationId} className={classNames('tr')}>
                            <div className={classNames('td')}>
                                <div>{item.reservationId}</div>
                            </div>
                            <div className={classNames('td')}>
                                <div>{item.shopName}</div>
                            </div>
                            <div className={classNames('td')}>
                                <div>{item.userName}</div>
                            </div>
                            <div className={classNames('td')}>
                                <div>{item.carModel}</div>
                            </div>
                            <div className={classNames('td')}>
                                <div>{item.startDate}</div>
                            </div>
                            <div className={classNames('td')}>
                                <div>{item.endDate}</div>
                            </div>
                            <div className={classNames('td')}>
                                <div>{item.stage}</div>
                            </div>
                        </div>
                    ))}
                </div>
                <div className={classNames('paginate')}>
                    <div>&lt;</div>
                    <div>1</div>
                    <div>2</div>
                    <div>3</div>
                    <div>4</div>
                    <div>5</div>
                    <div>&gt;</div>
                </div>
            </div>
        </div>
      </div>
    );
  }
  
