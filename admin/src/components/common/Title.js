import classNames from 'classnames'

const Title = ({ text }) => {
  return (
    <div className={classNames('title')}>
      <span>{text}</span>
    </div>
  )
}

export default Title
