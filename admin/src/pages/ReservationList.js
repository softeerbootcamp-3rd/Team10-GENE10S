import classNames from 'classnames';
import SideBar from "../components/SideBar";
import BtnDark from '../components/Button';
import CustomCalendar from '../components/CustomCalendar';
import React, { useState } from 'react';

export default function ReservationList() {
    const [selectedDate, setSelectedDate] = useState(null);
    const today = new Date();
    const next28Day = new Date(today.getTime() + (28 * 24 * 60 * 60 * 1000));

    const handleDateChange = (date) => {
        setSelectedDate(date);
    };

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
                            <input />
                        </div>
                    </div>
                    <div className={classNames('search-row')}>
                        <div className={classNames('search-item')}>
                            <div>센터 방문</div>
                            <CustomCalendar onChange={handleDateChange} value={selectedDate} />
                            <div>~</div>
                            <CustomCalendar onChange={handleDateChange} value={selectedDate} />
                        </div>
                        <div className={classNames('search-item')}>
                            <div>픽업</div>
                            <CustomCalendar onChange={handleDateChange} value={selectedDate} />
                            <div>~</div>
                            <CustomCalendar onChange={handleDateChange} value={selectedDate} />
                        </div>
                    </div>
                    <div className={classNames('search-row')}>
                        <div className={classNames('search-item')}>
                            <div>고객명</div>
                            <input />
                        </div>
                        <div className={classNames('search-item')}>
                            <div>차종</div>
                            <input />
                        </div>
                        <div className={classNames('search-item')}>
                            <div>진행 단계</div>
                            <input />
                        </div>
                        <BtnDark text={"검색"}/>
                    </div>
                </div>
                <div className={classNames('table')}>
                    <div className={classNames('th')}>
                        <div className={classNames('td')}>
                            <div className={classNames('no')}>No</div>
                        </div>
                        <div className={classNames('td')}>
                            <div>정비소</div>
                        </div>
                        <div className={classNames('td')}>
                            <div>고객명</div>
                        </div>
                        <div className={classNames('td')}>
                            <div>차종</div>
                        </div>
                        <div className={classNames('td')}>
                            <div>센터 방문</div>
                        </div>
                        <div className={classNames('td')}>
                            <div>픽업</div>
                        </div>
                        <div className={classNames('td')}>
                            <div>현재 단계</div>
                        </div>
                    </div>
                    <div className={classNames('tr')}>
                        <div className={classNames('td')}>
                            <div>10</div>
                        </div>
                        <div className={classNames('td')}>
                            <div>블루핸즈 인천공항점</div>
                        </div>
                        <div className={classNames('td')}>
                            <div>김희진</div>
                        </div>
                        <div className={classNames('td')}>
                            <div>Genesis G80</div>
                        </div>
                        <div className={classNames('td')}>
                            <div>2024-02-19 10:00</div>
                        </div>
                        <div className={classNames('td')}>
                            <div>2024-02-20 20:00</div>
                        </div>
                        <div className={classNames('td')}>
                            <div>예약 완료</div>
                        </div>
                    </div>
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
  

