import classNames from 'classnames'

const InfoSteps = ({ label, children }) => {
  return (
    <>
      <div className={classNames('td', 'header')}>{label}</div>
      <div className={classNames('td', 'reservation-steps')}>{children}</div>
    </>
  )
}

export default InfoSteps
