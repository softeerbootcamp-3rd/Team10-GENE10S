import classNames from 'classnames'
import BtnDelete from '../button/BtnDelete'

const InfoImage = ({ image, onDelete }) => {
  return (
    <div key={image.id} className={classNames('image')}>
      <img alt={image.id} src={image.url} />
      <BtnDelete onClick={onDelete} />
    </div>
  )
}

export default InfoImage
