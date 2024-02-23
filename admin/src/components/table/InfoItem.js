import classNames from 'classnames'

const InfoItem = ({ label, children }) => {
  return (
    <>
      <div className={classNames('td', 'header')}>
        <span>{label}</span>
      </div>
      <div className={classNames('td')}>{children}</div>
    </>
  )
}

export default InfoItem
