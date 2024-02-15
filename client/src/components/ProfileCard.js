import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import classNames from 'classnames';

const ProfileCard = ({ profileInfo, carList }) => {
  const [animate, setAnimate] = useState(false);
  const [infoAnimate, setInfoAnimate] = useState(false);

  useEffect(() => {
    // 컴포넌트가 마운트되면 애니메이션 트리거
    setAnimate(true);
  }, []);
  
  useEffect(() => {
    setInfoAnimate(true);
  }, [animate])

  return (
    <div className={classNames('content')}>
      <div className={classNames('profile-view', {'animate': animate})}>
        { carList && carList.length > 0 && (
          <img alt='car' src={carList[0].imageUrl} className={classNames('profile-image')} />
        )}
        <div className={classNames('profile-info')}>
          <div className={classNames('name', {'animate': infoAnimate})}>
            <div className={classNames('text')}>
              {profileInfo.name}
            </div>
            <Link to="/profile" className={classNames('setting')} />
          </div>
          <div className={classNames('email', {'animate': infoAnimate})}>{profileInfo.email}</div>
          <div className={classNames('cars', {'animate': infoAnimate})}>
            {carList.map((car, index) => (
              <div key={index} className={classNames('car-name')}>
                <div className={classNames('text')}>{car.sellName}</div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProfileCard;
